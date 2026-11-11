package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * iOS 悬浮按钮 + 描边卡片。
 */

@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    elevation: Any? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(shape ?: CircleShape)
            .background(containerColor)
            .clickable(
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) { content() }
    }
}

@Composable
fun ExtendedFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    elevation: Any? = null,
    interactionSource: MutableInteractionSource? = null,
    icon: @Composable () -> Unit = {},
    text: @Composable () -> Unit = {},
    expanded: Boolean = true,
    content: @Composable RowScope.() -> Unit = {},
) {
    Row(
        modifier = modifier
            .clip(shape ?: RoundedCornerShape(28.dp))
            .background(containerColor)
            .clickable(
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            icon()
            Spacer(Modifier.width(8.dp))
            text()
            content()
        }
    }
}

@Composable
fun OutlinedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    val bg = if (isDark()) Color(0xFF1C1C1E) else Color.White
    Column(
        modifier = modifier
            .clip(shape ?: RoundedCornerShape(20.dp))
            .background(bg)
            .then(
                if (border != null) Modifier.border(border, shape ?: RoundedCornerShape(20.dp))
                else Modifier.border(1.dp, if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA), shape ?: RoundedCornerShape(20.dp)),
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        content()
    }
}

@Composable
fun OutlinedCard(
    modifier: Modifier = Modifier,
    shape: Shape? = null,
    colors: Any? = null,
    elevation: Any? = null,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable androidx.compose.foundation.layout.ColumnScope.() -> Unit,
) {
    val bg = if (isDark()) Color(0xFF1C1C1E) else Color.White
    Column(
        modifier = modifier
            .clip(shape ?: RoundedCornerShape(20.dp))
            .background(bg)
            .then(
                if (border != null) Modifier.border(border, shape ?: RoundedCornerShape(20.dp))
                else Modifier.border(1.dp, if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA), shape ?: RoundedCornerShape(20.dp)),
            )
            .padding(contentPadding),
    ) {
        content()
    }
}
