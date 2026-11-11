package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
 * iOS 胶囊芯片：选中为蓝底白字，未选中为灰底。
 */

@Composable
private fun IosChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    shape: Shape?,
) {
    val active = if (isDark()) Color(0xFF0A84FF) else Color(0xFF007AFF)
    val inactive = if (isDark()) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)
    val bg = if (selected) active else inactive
    val fg = if (selected) Color.White else MaterialTheme.colorScheme.onSurface
    Row(
        modifier = modifier
            .clip(shape ?: RoundedCornerShape(50))
            .background(bg)
            .border(1.dp, if (selected) active else Color.Transparent, shape ?: RoundedCornerShape(50))
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides fg) {
            if (leadingIcon != null) {
                leadingIcon()
                Spacer(Modifier.width(6.dp))
            }
            label()
            if (trailingIcon != null) {
                Spacer(Modifier.width(6.dp))
                trailingIcon()
            }
        }
    }
}

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    IosChip(selected, onClick, label, modifier, enabled, leadingIcon, trailingIcon, shape)
}

@Composable
fun InputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    IosChip(selected, onClick, label, modifier, enabled, leadingIcon, trailingIcon, shape)
}

@Composable
fun AssistChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    IosChip(false, onClick, label, modifier, enabled, leadingIcon, trailingIcon, shape)
}
