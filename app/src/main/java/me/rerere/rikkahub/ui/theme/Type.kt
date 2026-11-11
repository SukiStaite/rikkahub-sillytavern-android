package me.rerere.rikkahub.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.rerere.rikkahub.R

/**
 * iOS 风格排版：使用 Inter 可变字体（SF Pro 的开源替代，OFL 许可），
 * 按 iOS SF 字体排版设定字号与字重（Large Title / Title / Body / Footnote 等）。
 * 行高取 SF 的近似倍率，整体贴近 iOS 原生观感。
 */

@OptIn(ExperimentalTextApi::class)
private fun interFont(weight: FontWeight): FontFamily {
    return FontFamily(
        Font(
            resId = R.font.inter_variable,
            weight = weight,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(weight.weight),
                FontVariation.opticalSizing(17.sp),
            ),
        )
    )
}

// SF 字重（近似）
private val W400 = FontWeight.Normal
private val W500 = FontWeight.Medium
private val W600 = FontWeight.SemiBold
private val W700 = FontWeight.Bold

private fun sfStyle(size: Int, weight: FontWeight, lineHeight: Float): TextStyle = TextStyle(
    fontSize = size.sp,
    fontWeight = weight,
    fontFamily = interFont(weight),
    lineHeight = (size * lineHeight).sp,
    letterSpacing = 0.sp,
)

// iOS SF 排版刻度（字号 sp / 字重 / 行高倍率）
private val iOSLargeTitle = sfStyle(34, W700, 1.18f)   // Large Title
private val iOSTitle1 = sfStyle(28, W700, 1.21f)       // Title 1
private val iOSTitle2 = sfStyle(22, W600, 1.27f)       // Title 2
private val iOSTitle3 = sfStyle(20, W600, 1.30f)       // Title 3
private val iOSHeadline = sfStyle(17, W600, 1.29f)     // Headline
private val iOSBody = sfStyle(17, W400, 1.29f)         // Body
private val iOSCallout = sfStyle(16, W400, 1.31f)      // Callout
private val iOSSubheadline = sfStyle(15, W400, 1.33f)  // Subheadline
private val iOSFootnote = sfStyle(13, W400, 1.38f)     // Footnote
private val iOSCaption1 = sfStyle(12, W400, 1.33f)     // Caption 1
private val iOSCaption2 = sfStyle(11, W400, 1.27f)     // Caption 2

/**
 * iOS 风格的 Material3 Typography。
 * 保留 Material3 的「强调」（emphasized）角色，但字号/字重按 SF 排版设定。
 */
val Typography = Typography(
    // display —— 大标题级（SF Large Title / Title 1）
    displayLarge = iOSLargeTitle,
    displayLargeEmphasized = iOSLargeTitle,
    displayMedium = iOSTitle1,
    displayMediumEmphasized = iOSTitle1,
    displaySmall = iOSTitle1,
    displaySmallEmphasized = iOSTitle1,
    // headline —— Title 级（SF Title 1 / Title 2）
    headlineLarge = iOSTitle1,
    headlineLargeEmphasized = iOSTitle1,
    headlineMedium = iOSTitle2,
    headlineMediumEmphasized = iOSTitle2,
    headlineSmall = iOSTitle3,
    headlineSmallEmphasized = iOSTitle3,
    // title —— 标题级（SF Title 3 / Headline）
    titleLarge = iOSTitle3,
    titleLargeEmphasized = iOSTitle3,
    titleMedium = iOSHeadline,
    titleMediumEmphasized = iOSHeadline,
    titleSmall = iOSSubheadline,
    titleSmallEmphasized = iOSSubheadline,
    // body —— 正文级（SF Body / Callout / Subheadline）
    bodyLarge = iOSBody,
    bodyLargeEmphasized = iOSBody,
    bodyMedium = iOSSubheadline,
    bodyMediumEmphasized = iOSSubheadline,
    bodySmall = iOSFootnote,
    bodySmallEmphasized = iOSFootnote,
    // label —— 按钮/标签级（SF Headline / Callout / Caption）
    labelLarge = iOSHeadline,
    labelLargeEmphasized = iOSHeadline,
    labelMedium = iOSCallout,
    labelMediumEmphasized = iOSCallout,
    labelSmall = iOSCaption1,
    labelSmallEmphasized = iOSCaption1,
)

@OptIn(ExperimentalTextApi::class)
val JetbrainsMono = FontFamily(
    Font(
        resId = R.font.jetbrains_mono,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(FontWeight.Normal.weight),
        )
    )
)
