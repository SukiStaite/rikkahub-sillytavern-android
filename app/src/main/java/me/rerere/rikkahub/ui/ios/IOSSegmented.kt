package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
 * iOS 分段控件：单选行 + 分段按钮 + 分段列表项。
 */

interface SingleChoiceSegmentedButtonRowScope

@Composable
fun SingleChoiceSegmentedButtonRow(
    modifier: Modifier = Modifier,
    content: @Composable SingleChoiceSegmentedButtonRowScope.() -> Unit,
) {
    val scope = object : SingleChoiceSegmentedButtonRowScope {}
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isDark()) Color(0xFF1C1C1E) else Color(0xFFF0F0F2))
            .padding(2.dp),
    ) {
        content(scope)
    }
}

@Composable
fun SingleChoiceSegmentedButtonRowScope.SegmentedButton(
    selected: Boolean,
    onClick: () -> Unit,
    shape: Shape? = null,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: Any? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
    interactionSource: MutableInteractionSource? = null,
    icon: @Composable () -> Unit = {},
    label: @Composable () -> Unit,
) {
    val active = if (isDark()) Color(0xFF2C2C2E) else Color.White
    val inactive = Color.Transparent
    val fg = MaterialTheme.colorScheme.onSurface
    Row(
        modifier = modifier
            .clip(shape ?: RoundedCornerShape(10.dp))
            .background(if (selected) active else inactive)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides fg) {
            icon()
            label()
        }
    }
}

@Composable
fun SegmentedListItem(
    modifier: Modifier = Modifier,
    selected: Boolean? = null,
    checked: Boolean? = null,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    shapes: Any? = null,
    colors: Any? = null,
    supportingContent: (@Composable () -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    headlineContent: @Composable () -> Unit,
) {
    val isSelected = selected ?: checked ?: false
    val bg = if (isSelected) {
        if (isDark()) Color(0xFF0A84FF) else Color(0xFF007AFF)
    } else {
        if (isDark()) Color(0xFF1C1C1E) else Color.White
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
            ) {
                onClick?.invoke()
                if (onCheckedChange != null) {
                    onCheckedChange(!isSelected)
                }
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingContent != null) {
            leadingContent()
        }
        androidx.compose.foundation.layout.Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            headlineContent()
            if (supportingContent != null) {
                supportingContent()
            }
        }
        if (trailingContent != null) {
            trailingContent()
        }
    }
}
