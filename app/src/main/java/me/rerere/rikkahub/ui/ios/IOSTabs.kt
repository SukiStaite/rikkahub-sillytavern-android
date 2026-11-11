package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * iOS 标签页：胶囊分段标签 + 可滚动标签行。
 */

@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    minTabWidth: Dp = 90.dp,
    text: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    selectedContentColor: Color = iosBlue(),
    unselectedContentColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val fg = if (selected) selectedContentColor else unselectedContentColor
    val bg = if (selected) {
        if (isDark()) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)
    } else Color.Transparent
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CompositionLocalProvider(LocalContentColor provides fg) {
            if (icon != null) {
                icon()
            }
            if (text != null) {
                text()
            }
            content()
        }
    }
}

@Composable
fun SecondaryScrollableTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    scrollState: androidx.compose.foundation.ScrollState = rememberScrollState(),
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    dividerColor: Color = MaterialTheme.colorScheme.outlineVariant,
    edgePadding: Dp = 16.dp,
    minTabWidth: Dp = 0.dp,
    indicator: @Composable () -> Unit = {},
    divider: @Composable () -> Unit = {},
    tabs: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(containerColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = edgePadding),
        ) {
            tabs()
        }
    }
}
