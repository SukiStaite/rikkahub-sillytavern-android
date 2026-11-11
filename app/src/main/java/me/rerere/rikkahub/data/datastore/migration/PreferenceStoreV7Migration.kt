package me.rerere.rikkahub.data.datastore.migration

import androidx.datastore.core.DataMigration
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import me.rerere.rikkahub.data.datastore.SettingsStore

/**
 * V7：iOS 全面改版。
 * 升级时强制切换到「iOS 系统色」主题，并关闭系统动态取色，
 * 保证升级后默认呈现纯 iOS 系统色，而不是沿用旧主题。
 */
class PreferenceStoreV7Migration : DataMigration<Preferences> {
    override suspend fun shouldMigrate(currentData: Preferences): Boolean {
        val version = currentData[SettingsStore.VERSION]
        return version == null || version < 7
    }

    override suspend fun migrate(currentData: Preferences): Preferences {
        val prefs = currentData.toMutablePreferences()
        prefs[SettingsStore.THEME_ID] = "ios"
        prefs[SettingsStore.DYNAMIC_COLOR] = false
        prefs[SettingsStore.VERSION] = 7
        return prefs.toPreferences()
    }

    override suspend fun cleanUp() {}
}
