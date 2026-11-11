package me.rerere.rikkahub.ui.ios

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

/**
 * iOS 风格组件库：替代 Material3 控件，视觉对齐 iOS 系统（开关/滑块/复选框/单选）。
 *
 * 组件名与 Material3 同名，仅更换 import 即可全局生效，
 * 调用点（Switch/Slider/...）无需改动。
 */

// iOS 系统色
private val IOSGreen = Color(0xFF34C759)
private val IOSSwitchOff = Color(0xFFE9E9EA)
private val IOSSwitchOffDark = Color(0xFF39393D)
private val IOSCheckUnchecked = Color(0xFFC7C7CC)
private val IOSCheckUncheckedDark = Color(0xFF48484A)

@Composable
internal fun iosBlue() = MaterialTheme.colorScheme.primary

@Composable
internal fun isDark() = MaterialTheme.colorScheme.surfaceContainerHigh.luminance() < 0.5f

/** iOS 开关：胶囊轨道 + 白色圆形滑钮，开启为 iOS 绿 */
@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val dark = isDark()
    val trackColor by animateColorAsState(
        targetValue = when {
            !enabled -> if (dark) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)
            checked -> IOSGreen
            else -> if (dark) IOSSwitchOffDark else IOSSwitchOff
        },
        label = "iosSwitchTrack",
    )
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 20.dp else 2.dp,
        label = "iosSwitchThumb",
    )
    Box(
        modifier = modifier
            .width(51.dp)
            .height(31.dp)
            .clip(RoundedCornerShape(15.5.dp))
            .background(trackColor)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onCheckedChange?.invoke(!checked) },
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .align(Alignment.CenterStart)
                .size(27.dp)
                .shadow(2.dp, CircleShape)
                .background(Color.White, CircleShape),
        )
    }
}

/** iOS 滑块：细轨道 + 白色圆钮，激活段 iOS 蓝，支持点按/拖拽 */
@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
) {
    val fraction = ((value - valueRange.start) / (valueRange.endInclusive - valueRange.start)).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .height(28.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .pointerInput(enabled, valueRange, steps) {
                if (!enabled) return@pointerInput
                val width = size.width.toFloat()
                val range = valueRange.endInclusive - valueRange.start
                fun f(x: Float): Float {
                    val raw = (x / width).coerceIn(0f, 1f)
                    val stepped = if (steps > 0) (raw * steps).roundToInt().toFloat() / steps else raw
                    return valueRange.start + stepped * range
                }
                awaitEachGesture {
                    val down = awaitFirstDown()
                    down.consume()
                    onValueChange(f(down.position.x))
                    while (true) {
                        val event = awaitPointerEvent()
                        val change = event.changes.firstOrNull() ?: break
                        if (change.changedToUp()) {
                            onValueChangeFinished?.invoke()
                            break
                        }
                        onValueChange(f(change.position.x))
                        change.consume()
                    }
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        // 轨道
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (enabled) MaterialTheme.colorScheme.surfaceContainerHighest else MaterialTheme.colorScheme.surfaceContainer),
        )
        // 激活段（左→圆钮）
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(if (enabled) iosBlue() else MaterialTheme.colorScheme.outline),
        )
        // 圆钮
        Box(
            modifier = Modifier
                .fillMaxWidth(fraction)
                .height(28.dp),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(26.dp)
                    .shadow(3.dp, CircleShape)
                    .background(if (enabled) Color.White else MaterialTheme.colorScheme.surfaceContainerHighest, CircleShape),
            )
        }
    }
}

/** iOS 复选框：圆角方块 + iOS 蓝填充 + 白色对勾 */
@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val dark = isDark()
    val bg = if (checked) iosBlue() else (if (dark) IOSCheckUncheckedDark else IOSCheckUnchecked)
    Box(
        modifier = modifier
            .size(22.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onCheckedChange?.invoke(!checked) },
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            Canvas(modifier = Modifier.size(14.dp)) {
                val stroke = 2.dp.toPx()
                val path = Path().apply {
                    moveTo(size.width * 0.15f, size.height * 0.55f)
                    lineTo(size.width * 0.42f, size.height * 0.8f)
                    lineTo(size.width * 0.88f, size.height * 0.22f)
                }
                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round),
                )
            }
        }
    }
}

/** iOS 单选：圆形描边 + 选中时 iOS 蓝内点 */
@Composable
fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Box(
        modifier = modifier
            .size(22.dp)
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) { onClick?.invoke() },
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .border(2.dp, iosBlue(), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(iosBlue(), CircleShape),
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .border(2.dp, if (enabled) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.outlineVariant, CircleShape),
            )
        }
    }
}
