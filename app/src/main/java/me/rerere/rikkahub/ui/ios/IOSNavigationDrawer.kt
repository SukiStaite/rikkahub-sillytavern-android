package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * iOS 侧边抽屉。
 */

@Composable
fun ModalNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    gesturesEnabled: Boolean = true,
    scrimColor: Color = Color.Black.copy(alpha = 0.4f),
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    Box(modifier = modifier) {
        if (drawerState.isOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor)
                    .clickable { scope.launch { drawerState.close() } },
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                    .background(if (isDark()) Color(0xFF1C1C1E) else Color.White),
            ) {
                drawerContent()
            }
        }
        content()
    }
}

@Composable
fun ModalDrawerSheet(
    modifier: Modifier = Modifier,
    drawerShape: Shape? = null,
    drawerContainerColor: Color = if (isDark()) Color(0xFF1C1C1E) else Color.White,
    drawerContentColor: Color = MaterialTheme.colorScheme.onSurface,
    drawerTonalElevation: androidx.compose.ui.unit.Dp = 0.dp,
    windowInsets: androidx.compose.foundation.layout.WindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(drawerShape ?: RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
            .background(drawerContainerColor),
    ) {
        CompositionLocalProvider(LocalContentColor provides drawerContentColor) {
            content()
        }
    }
}

@Composable
fun PermanentNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp),
        ) {
            drawerContent()
        }
        Box(modifier = Modifier.weight(1f)) {
            content()
        }
    }
}

@Composable
fun NavigationDrawerItem(
    label: @Composable () -> Unit,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    badge: (@Composable () -> Unit)? = null,
    shape: Shape? = null,
    colors: Any? = null,
    interactionSource: MutableInteractionSource? = null,
) {
    val active = if (isDark()) Color(0xFF0A84FF) else Color(0xFF007AFF)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape ?: RoundedCornerShape(12.dp))
            .background(if (selected) active.copy(alpha = 0.18f) else Color.Transparent)
            .clickable(
                interactionSource = interactionSource ?: remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            icon()
        }
        Box(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
            label()
        }
        if (badge != null) {
            badge()
        }
    }
}
