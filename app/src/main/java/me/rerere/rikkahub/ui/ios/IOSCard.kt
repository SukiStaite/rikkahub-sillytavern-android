package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.rerere.rikkahub.ui.theme.LocalMaterialMode
import me.rerere.rikkahub.ui.theme.isGlass

/**
 * iOS 风格卡片 / 表面：圆角玻璃卡片。
 * 与 Material3 同名；Material 专属参数（colors/elevation）接受但按 iOS 风格渲染。
 */

private val CardShape = RoundedCornerShape(20.dp)

@Composable
private fun cardColor(): Color {
    val base = if (isDark()) Color(0xFF1C1C1E) else Color.White
    return if (LocalMaterialMode.current.isGlass) base.copy(alpha = 0.62f) else base.copy(alpha = 0.85f)
}

@Composable
private fun glassBorder(): BorderStroke? =
    if (LocalMaterialMode.current.isGlass)
        BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.14f))
    else null

@Composable
private fun cardContentColor(): Color = MaterialTheme.colorScheme.onSurface

/** iOS 卡片（可点击） */
@Composable
fun Card(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val s = shape ?: CardShape
    val b = border ?: glassBorder()
    Column(
        modifier = modifier
            .shadow(2.dp, s)
            .clip(s)
            .background(cardColor())
            .then(if (b != null) Modifier.border(b, s) else Modifier)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides cardContentColor(),
        ) {
            content()
        }
    }
}

/** iOS 卡片（不可点击） */
@Composable
fun Card(
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val s = shape ?: CardShape
    val b = border ?: glassBorder()
    Column(
        modifier = modifier
            .shadow(2.dp, s)
            .clip(s)
            .background(cardColor())
            .then(if (b != null) Modifier.border(b, s) else Modifier),
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides cardContentColor(),
        ) {
            content()
        }
    }
}

/** iOS 表面（可点击） */
@Composable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    val bg = color
    val b = border ?: glassBorder()
    Box(
        modifier = modifier
            .then(if (shadowElevation > 0.dp) Modifier.shadow(shadowElevation, shape) else Modifier)
            .clip(shape)
            .background(bg)
            .then(if (b != null) Modifier.border(b, shape) else Modifier)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides contentColor,
        ) {
            content()
        }
    }
}

/** iOS 表面：圆角背景容器 */
@Composable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    alpha: Float? = null,
    content: @Composable () -> Unit,
) {
    val bg = if (alpha != null) color.copy(alpha = alpha) else color
    val b = border ?: glassBorder()
    Box(
        modifier = modifier
            .then(if (shadowElevation > 0.dp) Modifier.shadow(shadowElevation, shape) else Modifier)
            .clip(shape)
            .background(bg)
            .then(if (b != null) Modifier.border(b, shape) else Modifier),
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides contentColor,
        ) {
            content()
        }
    }
}

@Composable
fun ElevatedCard(
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(shape ?: RoundedCornerShape(20.dp))
            .background(if (isDark()) Color(0xFF1C1C1E) else Color.White),
        content = content,
    )
}
