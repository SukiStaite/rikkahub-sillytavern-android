package me.rerere.rikkahub.ui.theme

import android.os.Build
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import kotlinx.serialization.Serializable
import me.rerere.rikkahub.data.datastore.DisplayMaterialMode
import me.rerere.rikkahub.ui.components.ui.toComposeColor
import me.rerere.rikkahub.ui.context.LocalDisplaySettings
import me.rerere.rikkahub.ui.hooks.rememberAmoledDarkMode
import me.rerere.rikkahub.ui.hooks.rememberCurrentColorMode
import me.rerere.rikkahub.ui.hooks.rememberUserSettingsState
import me.rerere.rikkahub.utils.getActivity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

private val ExtendLightColors = lightExtendColors()
private val ExtendDarkColors = darkExtendColors()
val LocalExtendColors = compositionLocalOf { ExtendLightColors }

val LocalDarkMode = compositionLocalOf { false }

val LocalMaterialMode = compositionLocalOf { DisplayMaterialMode.FOLLOW_THEME }

// 设置页背景图生效时置位，页面可据此让自身底色透明以露出背景图
val LocalSettingsBackdropActive = compositionLocalOf { false }

const val LIQUID_GLASS_THEME_ID = "liquid_glass"

private val AMOLED_DARK_BACKGROUND = Color(0xFF000000)

// AMOLED 下让容器也贴近纯黑，否则设置页卡片仍是深灰，开关看上去没反应
private val AMOLED_DARK_CONTAINER_LOW = Color(0xFF000000)
private val AMOLED_DARK_CONTAINER = Color(0xFF000000)
private val AMOLED_DARK_CONTAINER_HIGH = Color(0xFF101010)
private val AMOLED_DARK_CONTAINER_HIGHEST = Color(0xFF161616)

@Serializable
enum class ColorMode {
    SYSTEM,
    LIGHT,
    DARK
}

@Composable
fun RikkahubTheme(
    colorMode: ColorMode = rememberCurrentColorMode(),
    content: @Composable () -> Unit
) {
    val settings by rememberUserSettingsState()

    val systemDark = when (colorMode) {
        ColorMode.SYSTEM -> isSystemInDarkTheme()
        ColorMode.LIGHT -> false
        ColorMode.DARK -> true
    }
    // 聊天外观自定义了聊天背景色时，全局深浅色跟随主题背景亮度，
    // 避免系统暗黑模式下顶栏/抽屉/菜单是深色而聊天区是浅色主题的错乱
    val chatBg = settings.displaySetting.chatBackgroundColor?.toComposeColor()
    val darkTheme = if (chatBg != null) {
        val luminance = 0.299f * chatBg.red + 0.587f * chatBg.green + 0.114f * chatBg.blue
        luminance < 0.5f
    } else {
        systemDark
    }
    val amoledDarkMode by rememberAmoledDarkMode()

    val materialMode = when (settings.displaySetting.materialMode) {
        // iOS 27 液态玻璃：默认 iOS 主题与 liquid_glass 主题都走 GLASS 材质，
        // 让全局表面（顶栏/卡片/输入框/底部栏）呈现半透明玻璃质感。
        DisplayMaterialMode.FOLLOW_THEME ->
            if (settings.themeId == LIQUID_GLASS_THEME_ID || settings.themeId == "ios") DisplayMaterialMode.GLASS
            else DisplayMaterialMode.FLAT

        else -> settings.displaySetting.materialMode
    }

    val colorScheme = when {
        // iOS 主题强制使用 iOS 系统色板，忽略系统动态取色（dynamicColor），
        // 保证「纯 iOS 系统色」始终生效。
        settings.themeId == "ios" -> {
            val theme = findThemeById("ios", settings.customThemes)
                ?: findPresetTheme("ios")
            theme.getColorScheme(dark = darkTheme)
        }
        settings.dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> {
            val theme = findThemeById(settings.themeId, settings.customThemes)
                ?: findPresetTheme(settings.themeId)
            theme.getColorScheme(dark = darkTheme)
        }
    }
    val colorSchemeConverted = remember(darkTheme, amoledDarkMode, colorScheme) {
        if (darkTheme && amoledDarkMode) {
            colorScheme.copy(
                background = AMOLED_DARK_BACKGROUND,
                surface = AMOLED_DARK_BACKGROUND,
                surfaceDim = AMOLED_DARK_BACKGROUND,
                surfaceContainerLowest = AMOLED_DARK_BACKGROUND,
                surfaceContainerLow = AMOLED_DARK_CONTAINER_LOW,
                surfaceContainer = AMOLED_DARK_CONTAINER,
                surfaceContainerHigh = AMOLED_DARK_CONTAINER_HIGH,
                surfaceContainerHighest = AMOLED_DARK_CONTAINER_HIGHEST,
                surfaceBright = AMOLED_DARK_CONTAINER_HIGHEST,
                surfaceVariant = AMOLED_DARK_CONTAINER_HIGH,
            )
        } else {
            colorScheme
        }
    }
    val extendColors = if (darkTheme) ExtendDarkColors else ExtendLightColors

    // 聊天外观自定义覆盖层：在 dynamicColor/themeId 色板之上最后 copy() 覆盖
    val finalColorScheme = remember(
        colorSchemeConverted,
        settings.displaySetting.primaryColor,
        settings.displaySetting.globalTextColor,
    ) {
        var scheme = colorSchemeConverted
        settings.displaySetting.primaryColor?.let { pc ->
            val primaryColor = pc.toComposeColor()
            val luminance = 0.299f * primaryColor.red + 0.587f * primaryColor.green + 0.114f * primaryColor.blue
            val onPrimary = if (luminance > 0.5f) Color.Black else Color.White
            scheme = scheme.copy(
                primary = primaryColor,
                onPrimary = onPrimary,
            )
        }
        settings.displaySetting.globalTextColor?.let { gtc ->
            val textColor = gtc.toComposeColor()
            scheme = scheme.copy(
                onBackground = textColor,
                onSurface = textColor,
                onSurfaceVariant = textColor,
            )
        }
        scheme
    }

    // 更新状态栏图标颜色
    val view = LocalView.current
    val activity = view.context.getActivity()
    // 浮窗可能使用 Application Context，没有可更新系统栏的 Activity。
    if (!view.isInEditMode && activity != null) {
        DisposableEffect(view, activity, darkTheme) {
            val window = activity.window
            val controller = WindowCompat.getInsetsController(window, view)
            val previousLightStatusBars = controller.isAppearanceLightStatusBars
            val previousLightNavigationBars = controller.isAppearanceLightNavigationBars
            controller.apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
            onDispose {
                // 嵌套主题（如终端的深色主题）退出时恢复原有系统栏图标颜色。
                controller.isAppearanceLightStatusBars = previousLightStatusBars
                controller.isAppearanceLightNavigationBars = previousLightNavigationBars
            }
        }
    }

    CompositionLocalProvider(
        LocalDarkMode provides darkTheme,
        LocalExtendColors provides extendColors,
        LocalMaterialMode provides materialMode,
        LocalDisplaySettings provides settings.displaySetting,
        LocalOverscrollFactory provides null,
    ) {
        MaterialExpressiveTheme(
            colorScheme = finalColorScheme,
            typography = if (settings.useSystemFont) systemTypography else Typography,
            // 必须显式给一套 Shapes。
            //
            // MaterialExpressiveTheme 的默认形状是给「表达性」场景设计的：
            // 按钮是全圆角胶囊、卡片 28dp。放在这个以信息密度为主的聊天
            // 界面里，观感是「每个按钮都鼓成了药丸」，暗色下尤其明显——
            // 深色背景把圆角的边缘衬得更突出，视觉上像是画错了。
            //
            // iOS 风格：按 Apple 的圆角规范（HIG）设置——小控件 6-8dp、
            // 按钮/输入框 10dp、卡片/分组 13dp、大容器/弹层 18dp。
            // 层级关系（小 < 中 < 大）保持不变，Material 的语义才成立。
            shapes = Shapes(
                extraSmall = RoundedCornerShape(6.dp),
                small = RoundedCornerShape(8.dp),
                medium = RoundedCornerShape(10.dp),
                large = RoundedCornerShape(13.dp),
                extraLarge = RoundedCornerShape(18.dp),
            ),
            content = content,
            motionScheme = MotionScheme.expressive()
        )
    }
}

val MaterialTheme.extendColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendColors.current
