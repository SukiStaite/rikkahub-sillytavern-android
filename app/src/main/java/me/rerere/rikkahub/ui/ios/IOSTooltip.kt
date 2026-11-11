package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider

/**
 * iOS 提示气泡。
 */

interface TooltipScope

@Composable
fun TooltipBox(
    positionProvider: PopupPositionProvider,
    tooltip: @Composable TooltipScope.() -> Unit,
    state: TooltipState,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    enableUserInput: Boolean = true,
    isHoverableInTooltipAnchor: Boolean = true,
    isClippingEnabled: Boolean = false,
    content: @Composable () -> Unit,
) {
    val scope = object : TooltipScope {}
    Box(modifier = modifier) {
        content()
        if (state.isVisible) {
            Popup(
                onDismissRequest = onDismissRequest,
                popupPositionProvider = positionProvider,
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isDark()) Color(0xFF2C2C2E) else Color(0xFF1C1C1E))
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                ) {
                    tooltip(scope)
                }
            }
        }
    }
}

@Composable
fun Tooltip(
    tooltip: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        content()
    }
}

@Composable
fun TooltipScope.RichTooltip(
    title: @Composable () -> Unit,
    text: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
) {
    Column(modifier = modifier) {
        title()
        text()
        if (action != null) {
            action()
        }
    }
}
