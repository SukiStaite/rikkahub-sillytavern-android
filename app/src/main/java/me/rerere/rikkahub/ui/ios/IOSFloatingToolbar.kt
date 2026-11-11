package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * iOS 悬浮工具栏（选中文本的复制/剪切/粘贴条）。
 */

@Composable
fun HorizontalFloatingToolbar(
    expanded: Boolean,
    modifier: Modifier = Modifier,
    colors: Any? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
    scrollBehavior: Any? = null,
    shape: Shape? = null,
    leadingContent: @Composable RowScope.() -> Unit = {},
    trailingContent: @Composable RowScope.() -> Unit = {},
    floatingActionButton: @Composable RowScope.() -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    if (expanded) {
        Row(
            modifier = modifier
                .clip(shape ?: RoundedCornerShape(20.dp))
                .background(if (isDark()) Color(0xFF2C2C2E) else Color(0xFF1C1C1E))
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            leadingContent()
            content()
            trailingContent()
            floatingActionButton()
        }
    }
}
