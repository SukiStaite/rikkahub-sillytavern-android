package me.rerere.rikkahub.ui.ios

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * iOS 杂项：细分割线 + iOS 转圈指示器。
 */

/** iOS 分割线：1dp 细灰线 */
@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
    alpha: Float = 1f,
) {
    Box(
        modifier = modifier
            .height(thickness)
            .clip(RoundedCornerShape(thickness / 2))
            .background(color.copy(alpha = alpha)),
    )
}

/** iOS 转圈（有进度）：圆弧 + 进度 */
@Composable
fun CircularProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 3.dp,
    trackColor: Color = Color.Transparent,
    strokeCap: StrokeCap = StrokeCap.Round,
    gapSize: Dp = 4.dp,
) {
    val p = progress().coerceIn(0f, 1f)
    Box(modifier = modifier.size(36.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(36.dp)) {
            val stroke = strokeWidth.toPx()
            val arcSize = size.minDimension - stroke
            if (trackColor != Color.Transparent) {
                drawArc(
                    color = trackColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = stroke, cap = strokeCap),
                    topLeft = Offset(stroke / 2, stroke / 2),
                    size = Size(arcSize, arcSize),
                )
            }
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360f * p,
                useCenter = false,
                style = Stroke(width = stroke, cap = strokeCap),
                topLeft = Offset(stroke / 2, stroke / 2),
                size = Size(arcSize, arcSize),
            )
        }
    }
}

/** iOS 转圈：旋转圆弧 */
@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 3.dp,
    trackColor: Color = Color.Transparent,
    strokeCap: StrokeCap = StrokeCap.Round,
    gapSize: Dp = 4.dp,
) {
    val transition = rememberInfiniteTransition(label = "iosSpinner")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing)),
        label = "iosSpinnerRot",
    )
    Box(modifier = modifier.size(36.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(36.dp).rotate(rotation)) {
            val stroke = strokeWidth.toPx()
            val arcSize = size.minDimension - stroke
            drawArc(
                color = color,
                startAngle = 0f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = stroke, cap = strokeCap),
                topLeft = Offset(stroke / 2, stroke / 2),
                size = Size(arcSize, arcSize),
            )
        }
    }
}
