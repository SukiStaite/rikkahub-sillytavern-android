package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * iOS 脚手架 + 顶部导航栏。
 * Scaffold：topBar 在顶、bottomBar 在底、内容居中。
 * TopAppBar / LargeFlexibleTopAppBar：iOS 导航条（返回键 + 标题 + 操作）。
 */

@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: androidx.compose.material3.FabPosition = androidx.compose.material3.FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    contentWindowInsets: WindowInsets = androidx.compose.material3.ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(containerColor),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            topBar()
            Box(modifier = Modifier.weight(1f)) {
                content(PaddingValues(0.dp))
            }
            snackbarHost()
            bottomBar()
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
        ) {
            floatingActionButton()
        }
    }
}

@Composable
private fun iosBarBackground(): Color =
    if (isDark()) Color(0xFF161618) else Color(0xFFF8F8FA)

@Composable
private fun TopBarChrome(
    title: @Composable () -> Unit,
    modifier: Modifier,
    navigationIcon: (@Composable () -> Unit)?,
    actions: @Composable RowScope.() -> Unit,
    large: Boolean,
    subtitle: (@Composable () -> Unit)?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(iosBarBackground())
            .statusBarsPadding()
            .padding(
                horizontal = if (large) 16.dp else 4.dp,
                vertical = 6.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (navigationIcon != null) {
            navigationIcon()
        }
        Column(modifier = Modifier.weight(1f)) {
            title()
            if (subtitle != null) {
                subtitle()
            }
        }
        actions()
    }
}

@Composable
fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    subtitle: (@Composable () -> Unit)? = null,
    expandedHeight: Dp = androidx.compose.material3.TopAppBarDefaults.TopAppBarExpandedHeight,
    colors: Any? = null,
    scrollBehavior: Any? = null,
    windowInsets: WindowInsets = androidx.compose.material3.TopAppBarDefaults.windowInsets,
) {
    TopBarChrome(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        large = false,
        subtitle = subtitle,
    )
}

@Composable
fun LargeFlexibleTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    subtitle: (@Composable () -> Unit)? = null,
    expandedHeight: Dp = 152.dp,
    collapsedHeight: Dp = 64.dp,
    colors: Any? = null,
    scrollBehavior: Any? = null,
    windowInsets: WindowInsets = androidx.compose.material3.TopAppBarDefaults.windowInsets,
) {
    TopBarChrome(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        large = true,
        subtitle = subtitle,
    )
}
