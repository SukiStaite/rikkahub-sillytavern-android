package me.rerere.rikkahub.data.datastore.migration

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import me.rerere.rikkahub.data.datastore.BackgroundEffectType
import me.rerere.rikkahub.data.datastore.DisplaySetting
import me.rerere.rikkahub.data.datastore.SettingsStore
import me.rerere.rikkahub.utils.JsonInstant

/**
 * V8：iOS 27 液态玻璃默认开启。
 * 升级时强制打开输入框毛玻璃（GLASS 材质），保证升级后默认呈现液态玻璃观感，
 * 而不沿用升级前用户手动关闭的实色输入框。
 */
class PreferenceStoreV8Migration : DataMigration<Preferences> {
    override suspend fun shouldMigrate(currentData: Preferences): Boolean {
        val version = currentData[SettingsStore.VERSION]
        return version == null || version < 8
    }

    override suspend fun migrate(currentData: Preferences): Preferences {
        val prefs = currentData.toMutablePreferences()
        // 强制液态玻璃：输入框 blur 开启、GLASS 材质。
        // DISPLAY_SETTING 是独立 JSON 键，直接改它；失败则保持原样（用户可手动开）。
        runCatching {
            val json = prefs[SettingsStore.DISPLAY_SETTING]
            if (json != null) {
                val ds = JsonInstant.decodeFromString<DisplaySetting>(json)
                prefs[SettingsStore.DISPLAY_SETTING] = JsonInstant.encodeToString(
                    ds.copy(
                        enableBlurEffect = true,
                        backgroundEffectType = BackgroundEffectType.GLASS,
                    )
                )
            }
        }
        prefs[SettingsStore.VERSION] = 8
        return prefs.toPreferences()
    }

    override suspend fun cleanUp() {}
}
