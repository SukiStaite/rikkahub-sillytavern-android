package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
 * iOS 实心 / 实心色调图标按钮。
 */

@Composable
private fun IosFilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    shape: Shape?,
    colors: Any?,
    containerColor: Color?,
    contentColor: Color?,
    interactionSource: MutableInteractionSource?,
    content: @Composable () -> Unit,
) {
    val bg = containerColor ?: if (isDark()) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)
    val fg = contentColor ?: MaterialTheme.colorScheme.onSurface
    Box(
        modifier = modifier
            .size(48.dp)
            .clip(shape ?: CircleShape)
            .background(bg)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides fg) { content() }
    }
}

@Composable
fun FilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    shapes: Any? = null,
    colors: Any? = null,
    interactionSource: MutableInteractionSource? = null,
    containerColor: Color? = null,
    contentColor: Color? = null,
    content: @Composable () -> Unit,
) {
    IosFilledIconButton(onClick, modifier, enabled, shape, colors, containerColor, contentColor, interactionSource, content)
}

@Composable
fun FilledTonalIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    shapes: Any? = null,
    colors: Any? = null,
    interactionSource: MutableInteractionSource? = null,
    containerColor: Color? = null,
    contentColor: Color? = null,
    content: @Composable () -> Unit,
) {
    IosFilledIconButton(onClick, modifier, enabled, shape, colors, containerColor, contentColor, interactionSource, content)
}

@Composable
fun FilledTonalIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shapes: Any? = null,
    colors: Any? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    IosFilledIconButton(
        onClick = { onCheckedChange(!checked) },
        modifier = modifier,
        enabled = enabled,
        shape = null,
        colors = colors,
        interactionSource = interactionSource,
        containerColor = if (checked) {
            if (isDark()) Color(0xFF0A84FF) else Color(0xFF007AFF)
        } else {
            if (isDark()) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)
        },
        contentColor = null,
        content = content,
    )
}
