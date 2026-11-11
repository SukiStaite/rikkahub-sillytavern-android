package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * iOS 滑动删除容器（简化：内容叠放在背景上）。
 */

@Composable
fun SwipeToDismissBox(
    state: SwipeToDismissBoxState,
    backgroundContent: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    enableDismissFromStartToEnd: Boolean = true,
    enableDismissFromEndToStart: Boolean = true,
    gesturesEnabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    Box(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            backgroundContent()
        }
        Row(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}
