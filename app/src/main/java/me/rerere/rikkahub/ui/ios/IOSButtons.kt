package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * iOS 风格按钮：胶囊形，无投影，加粗标签。
 * 与 Material3 同名，兼容其调用签名；Material 专属参数（shapes/colors/elevation）忽略。
 */

private val CapsuleShape: Shape = RoundedCornerShape(percent = 50)

@Composable
private fun iosButtonBase(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    background: Color,
    contentColor: Color,
    border: BorderStroke?,
    contentPadding: PaddingValues,
    interactionSource: MutableInteractionSource?,
    content: @Composable RowScope.() -> Unit,
) {
    val base = modifier
        .clip(CapsuleShape)
        .then(if (border != null) Modifier.border(border, CapsuleShape) else Modifier)
        .background(background)
        .clickable(
            enabled = enabled,
            interactionSource = interactionSource ?: remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        )
        .padding(contentPadding)
    Row(
        modifier = base,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

/** iOS 填充按钮：系统蓝胶囊 + 白字 */
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Any? = null,
    shapes: Any? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val dark = isDark()
    iosButtonBase(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        background = if (enabled) iosBlue() else (if (dark) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)),
        contentColor = if (enabled) Color.White else (if (dark) Color(0xFF6C6C70) else Color(0xFF8E8E93)),
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

/** iOS 灰色（tonal）按钮：浅灰胶囊 + 蓝字 */
@Composable
fun FilledTonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Any? = null,
    shapes: Any? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val dark = isDark()
    iosButtonBase(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        background = if (enabled) (if (dark) Color(0xFF3A3A3C) else Color(0xFFE9E9EA)) else (if (dark) Color(0xFF2C2C2E) else Color(0xFFF2F2F2)),
        contentColor = if (enabled) iosBlue() else MaterialTheme.colorScheme.onSurface,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

/** iOS 描边按钮：蓝描边胶囊 + 蓝字 */
@Composable
fun OutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Any? = null,
    shapes: Any? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val dark = isDark()
    iosButtonBase(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        background = Color.Transparent,
        contentColor = if (enabled) iosBlue() else (if (dark) Color(0xFF48484A) else Color(0xFFC7C7CC)),
        border = border ?: BorderStroke(1.dp, if (enabled) iosBlue() else (if (dark) Color(0xFF48484A) else Color(0xFFC7C7CC))),
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}

/** iOS 文本按钮：透明背景 + 蓝字 */
@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Any? = null,
    shapes: Any? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val dark = isDark()
    iosButtonBase(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        background = Color.Transparent,
        contentColor = if (enabled) iosBlue() else (if (dark) Color(0xFF48484A) else Color(0xFFC7C7CC)),
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )
}
