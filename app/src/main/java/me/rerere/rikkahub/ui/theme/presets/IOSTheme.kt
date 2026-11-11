package me.rerere.rikkahub.ui.theme.presets

import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import me.rerere.rikkahub.R
import me.rerere.rikkahub.ui.theme.PresetTheme

/**
 * iOS 主题：纯 Apple 系统色板（systemBlue 主色、systemGroupedBackground 底色、
 * 分组列表 secondarySystemGroupedBackground 卡片色），整体贴近 iOS 原生观感。
 */
val IOSThemePreset by lazy {
    PresetTheme(
        id = "ios",
        name = {
            Text(stringResource(id = R.string.theme_name_ios))
        },
        standardLight = iosLightScheme,
        standardDark = iosDarkScheme,
    )
}

// ============ iOS 系统色（Light） ============
private val systemBlueLight = Color(0xFF007AFF)
private val systemGreenLight = Color(0xFF34C759)
private val systemRedLight = Color(0xFFFF3B30)
private val systemOrangeLight = Color(0xFFFF9500)
private val systemYellowLight = Color(0xFFFFCC00)
private val systemPurpleLight = Color(0xFFAF52DE)
private val systemTealLight = Color(0xFF5AC8FA)
private val systemPinkLight = Color(0xFFFF2D55)
private val systemGrayLight = Color(0xFF8E8E93)
private val systemGray2Light = Color(0xFFAEAEB2)
private val systemGray3Light = Color(0xFFC7C7CC)
private val systemGray4Light = Color(0xFFD1D1D6)
private val systemGray5Light = Color(0xFFE5E5EA)
private val systemGray6Light = Color(0xFFF2F2F7)

private val systemBackgroundLight = Color(0xFFFFFFFF)
private val secondarySystemBackgroundLight = Color(0xFFF2F2F7)
private val tertiarySystemBackgroundLight = Color(0xFFFFFFFF)
private val systemGroupedBackgroundLight = Color(0xFFF2F2F7)
private val secondarySystemGroupedBackgroundLight = Color(0xFFFFFFFF)
private val tertiarySystemGroupedBackgroundLight = Color(0xFFFFFFFF)

private val separatorLight = Color(0x4A3C3C43) // rgba(60,60,67,0.29)
private val opaqueSeparatorLight = Color(0xFFC6C6C8)
private val labelLight = Color(0xFF1C1C1E)
private val secondaryLabelLight = Color(0xFF3C3C43)
private val tertiaryLabelLight = Color(0xFF8E8E93)
private val systemFillLight = Color(0x33787880) // rgba(120,120,128,0.2)
private val secondarySystemFillLight = Color(0x29787880) // rgba(120,120,128,0.16)

// ============ iOS 系统色（Dark） ============
private val systemBlueDark = Color(0xFF0A84FF)
private val systemGreenDark = Color(0xFF30D158)
private val systemRedDark = Color(0xFFFF453A)
private val systemOrangeDark = Color(0xFFFF9F0A)
private val systemYellowDark = Color(0xFFFFD60A)
private val systemPurpleDark = Color(0xFFBF5AF2)
private val systemTealDark = Color(0xFF64D2FF)
private val systemPinkDark = Color(0xFFFF375F)
private val systemGrayDark = Color(0xFF8E8E93)
private val systemGray2Dark = Color(0xFF636366)
private val systemGray3Dark = Color(0xFF48484A)
private val systemGray4Dark = Color(0xFF3A3A3C)
private val systemGray5Dark = Color(0xFF2C2C2E)
private val systemGray6Dark = Color(0xFF1C1C1E)

private val systemBackgroundDark = Color(0xFF000000)
private val secondarySystemBackgroundDark = Color(0xFF1C1C1E)
private val tertiarySystemBackgroundDark = Color(0xFF2C2C2E)
private val systemGroupedBackgroundDark = Color(0xFF000000)
private val secondarySystemGroupedBackgroundDark = Color(0xFF1C1C1E)
private val tertiarySystemGroupedBackgroundDark = Color(0xFF2C2C2E)

private val separatorDark = Color(0x99545488) // rgba(84,84,88,0.6)
private val opaqueSeparatorDark = Color(0xFF38383A)
private val labelDark = Color(0xFFFFFFFF)
private val secondaryLabelDark = Color(0xFFEBEBF5)
private val tertiaryLabelDark = Color(0xFF8E8E93)
private val systemFillDark = Color(0x5C787880) // rgba(120,120,128,0.36)
private val secondarySystemFillDark = Color(0x52787880) // rgba(120,120,128,0.32)

private val iosLightScheme by lazy {
    lightColorScheme(
        primary = systemBlueLight,
        onPrimary = Color.White,
        primaryContainer = systemBlueLight,
        onPrimaryContainer = Color.White,
        secondary = systemGrayLight,
        onSecondary = Color.White,
        secondaryContainer = systemGray5Light,
        onSecondaryContainer = labelLight,
        tertiary = systemPurpleLight,
        onTertiary = Color.White,
        tertiaryContainer = systemGray5Light,
        onTertiaryContainer = labelLight,
        error = systemRedLight,
        onError = Color.White,
        errorContainer = Color(0xFFFFDAD6),
        onErrorContainer = Color(0xFF410002),
        background = systemGroupedBackgroundLight,
        onBackground = labelLight,
        surface = systemBackgroundLight,
        onSurface = labelLight,
        surfaceVariant = secondarySystemGroupedBackgroundLight,
        onSurfaceVariant = secondaryLabelLight,
        surfaceTint = systemBlueLight,
        inverseSurface = Color(0xFF2C2C2E),
        inverseOnSurface = Color(0xFFF2F2F7),
        inversePrimary = systemBlueDark,
        outline = opaqueSeparatorLight,
        outlineVariant = systemGray5Light,
        scrim = Color.Black,
        surfaceDim = systemGray5Light,
        surfaceBright = systemBackgroundLight,
        surfaceContainerLowest = systemBackgroundLight,
        surfaceContainerLow = secondarySystemGroupedBackgroundLight,
        surfaceContainer = systemGroupedBackgroundLight,
        surfaceContainerHigh = systemGray5Light,
        surfaceContainerHighest = systemGray4Light,
    )
}

private val iosDarkScheme by lazy {
    darkColorScheme(
        primary = systemBlueDark,
        onPrimary = Color.White,
        primaryContainer = systemBlueDark,
        onPrimaryContainer = Color.White,
        secondary = systemGrayDark,
        onSecondary = Color.White,
        secondaryContainer = systemGray5Dark,
        onSecondaryContainer = labelDark,
        tertiary = systemPurpleDark,
        onTertiary = Color.White,
        tertiaryContainer = systemGray5Dark,
        onTertiaryContainer = labelDark,
        error = systemRedDark,
        onError = Color.White,
        errorContainer = Color(0xFF5C1B17),
        onErrorContainer = Color(0xFFFFDAD6),
        background = systemGroupedBackgroundDark,
        onBackground = labelDark,
        surface = systemBackgroundDark,
        onSurface = labelDark,
        surfaceVariant = secondarySystemGroupedBackgroundDark,
        onSurfaceVariant = secondaryLabelDark,
        surfaceTint = systemBlueDark,
        inverseSurface = Color(0xFFF2F2F7),
        inverseOnSurface = Color(0xFF1C1C1E),
        inversePrimary = systemBlueLight,
        outline = opaqueSeparatorDark,
        outlineVariant = systemGray5Dark,
        scrim = Color.Black,
        surfaceDim = Color(0xFF000000),
        surfaceBright = systemGray5Dark,
        surfaceContainerLowest = Color(0xFF000000),
        surfaceContainerLow = secondarySystemGroupedBackgroundDark,
        surfaceContainer = secondarySystemGroupedBackgroundDark,
        surfaceContainerHigh = systemGray5Dark,
        surfaceContainerHighest = systemGray4Dark,
    )
}
