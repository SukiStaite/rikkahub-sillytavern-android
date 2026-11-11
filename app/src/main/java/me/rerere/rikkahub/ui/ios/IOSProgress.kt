package me.rerere.rikkahub.ui.ios

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

/**
 * iOS 线性进度条 + 底部提示条。
 */

@Composable
private fun IosLinearTrack(
    fraction: () -> Float,
    modifier: Modifier,
    color: Color,
    trackColor: Color,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(CircleShape)
            .background(trackColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction().coerceIn(0f, 1f))
                .fillMaxHeight()
                .clip(CircleShape)
                .background(color),
        )
    }
}

@Composable
fun LinearProgressIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
    trackColor: Color = if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA),
    strokeCap: Any? = null,
    gapSize: Any? = null,
    drawStopIndicator: Any? = null,
) {
    IosLinearTrack(progress, modifier, color, trackColor)
}

@Composable
fun LinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
    trackColor: Color = if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA),
    strokeCap: Any? = null,
) {
    IosLinearTrack({ progress }, modifier, color, trackColor)
}

@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = iosBlue(),
    trackColor: Color = if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA),
    strokeCap: Any? = null,
    gapSize: Any? = null,
    drawStopIndicator: Any? = null,
) {
    val transition = rememberInfiniteTransition(label = "ios_linear")
    val x by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing), RepeatMode.Restart),
        label = "ios_linear_anim",
    )
    IosLinearTrack({ x }, modifier, color, trackColor)
}

@Composable
fun SnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    snackbar: @Composable (SnackbarData) -> Unit = { data -> DefaultIosSnackbar(data) },
) {
    val data = hostState.currentSnackbarData
    if (data != null) {
        snackbar(data)
    }
}

@Composable
private fun DefaultIosSnackbar(data: SnackbarData) {
    val bg = if (isDark()) Color(0xFF2C2C2E) else Color(0xFF1C1C1E)
    val actionLabel = data.visuals.actionLabel
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(CircleShape)
            .background(bg)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        androidx.compose.material3.Text(
            text = data.visuals.message,
            color = Color.White,
            modifier = Modifier.weight(1f),
        )
        if (actionLabel != null) {
            androidx.compose.material3.Text(
                text = actionLabel,
                color = iosBlue(),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clip(CircleShape)
                    .clickable { data.performAction() }
                    .padding(8.dp),
            )
        }
    }
}
