package me.rerere.rikkahub.ui.ios

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * iOS 分段切换按钮 + 加载指示 + 角标。
 */

@Composable
fun ToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    enabled: Boolean = true,
    buttonSize: Any? = null,
    icon: (@Composable () -> Unit)? = null,
    shapes: Any? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val active = if (isDark()) Color(0xFF0A84FF) else Color(0xFF007AFF)
    val inactive = if (isDark()) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)
    val bg = if (checked) active else inactive
    val fg = if (checked) Color.White else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
            ) { onCheckedChange(!checked) }
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides fg) {
            if (icon != null) {
                icon()
            }
            content()
        }
    }
}

@Composable
fun ContainedLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
    strokeWidth: androidx.compose.ui.unit.Dp = 4.dp,
    trackColor: Color = Color.Transparent,
    strokeCap: StrokeCap = StrokeCap.Round,
    trackStrokeWidth: androidx.compose.ui.unit.Dp = 0.dp,
    trackStrokeCap: StrokeCap = StrokeCap.Round,
    containerColor: Color = if (isDark()) Color(0xFF1C1C1E) else Color(0xFFF0F0F2),
) {
    val transition = rememberInfiniteTransition(label = "ios_contained")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing)),
        label = "ios_contained_angle",
    )
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(containerColor)
            .size(28.dp)
            .padding(5.dp),
        contentAlignment = Alignment.Center,
    ) {
        androidx.compose.foundation.Canvas(Modifier.fillMaxSize()) {
            drawArc(
                color = color,
                startAngle = angle,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = strokeCap),
            )
        }
    }
}

@Composable
fun LinearWavyProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
    trackColor: Color = if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA),
) {
    LinearProgressIndicator(progress = progress, modifier = modifier, color = color, trackColor = trackColor)
}

@Composable
fun LinearWavyProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
    trackColor: Color = if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA),
) {
    LinearProgressIndicator(progress = progress, modifier = modifier, color = color, trackColor = trackColor)
}

@Composable
fun LinearWavyProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
    trackColor: Color = if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA),
) {
    LinearProgressIndicator(modifier = modifier, color = color, trackColor = trackColor)
}

@Composable
fun CircularWavyProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
) {
    CircularProgressIndicator(modifier = modifier, color = color)
}

@Composable
fun Badge(
    modifier: Modifier = Modifier,
    containerColor: Color = iosBlue(),
    contentColor: Color = Color.White,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(containerColor)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) { content() }
    }
}

@Composable
fun BadgedBox(
    badge: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier) {
        content()
        Box(modifier = Modifier.align(Alignment.TopEnd)) {
            badge()
        }
    }
}

@Composable
fun SuggestionChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    val inactive = if (isDark()) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)
    Row(
        modifier = modifier
            .clip(shape ?: RoundedCornerShape(50))
            .background(inactive)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        label()
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}
