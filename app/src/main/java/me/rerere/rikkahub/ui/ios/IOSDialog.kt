package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * iOS 风格对话框 / 底部面板。
 * AlertDialog：居中圆角卡片（iOS alert）。
 * ModalBottomSheet：底部圆角玻璃面板 + 拖拽条。
 */

@Composable
private fun iosDialogColor(): Color =
    if (isDark()) Color(0xFF1C1C1E) else Color.White

/** iOS 居中 alert */
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    dismissButton: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
    shape: Shape? = null,
    containerColor: Color = iosDialogColor(),
    iconContentColor: Color = MaterialTheme.colorScheme.onSurface,
    titleContentColor: Color = MaterialTheme.colorScheme.onSurface,
    textContentColor: Color = MaterialTheme.colorScheme.onSurface,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
) {
    Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        val s = shape ?: RoundedCornerShape(14.dp)
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .clip(s)
                .background(containerColor),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (icon != null) {
                    CompositionLocalProvider(androidx.compose.material3.LocalContentColor provides iconContentColor) { icon() }
                    Spacer(Modifier.height(12.dp))
                }
                if (title != null) {
                    CompositionLocalProvider(androidx.compose.material3.LocalContentColor provides titleContentColor) { title() }
                    if (text != null) Spacer(Modifier.height(6.dp))
                }
                if (text != null) {
                    CompositionLocalProvider(androidx.compose.material3.LocalContentColor provides textContentColor) { text() }
                }
            }
            if (confirmButton != null || dismissButton != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA)),
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (dismissButton != null) {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            dismissButton()
                        }
                    }
                    if (dismissButton != null && confirmButton != null) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(44.dp)
                                .background(if (isDark()) Color(0xFF3A3A3C) else Color(0xFFE5E5EA)),
                        )
                    }
                    if (confirmButton != null) {
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            confirmButton()
                        }
                    }
                }
            }
        }
    }
}

/** iOS 底部玻璃面板 */
@Composable
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    sheetMaxWidth: Dp = 640.dp,
    shape: Shape? = null,
    containerColor: Color = if (isDark()) Color(0xFF1C1C1E) else Color.White,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    tonalElevation: Dp = 0.dp,
    scrimColor: Color = Color(0x99000000),
    dragHandle: (@Composable () -> Unit)? = { DefaultDragHandle() },
    contentWindowInsets: @Composable () -> WindowInsets = { WindowInsets(0.dp, 0.dp, 0.dp, 0.dp) },
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    sheetGesturesEnabled: Boolean = true,
    skipPartiallyExpanded: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    val s = shape ?: RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = true),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // 遮罩
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor),
            )
            // 面板
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(s)
                    .background(containerColor),
            ) {
                if (dragHandle != null) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        dragHandle()
                    }
                }
                CompositionLocalProvider(androidx.compose.material3.LocalContentColor provides contentColor) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun DefaultDragHandle() {
    Box(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp)
            .width(36.dp)
            .height(5.dp)
            .clip(RoundedCornerShape(2.5.dp))
            .background(if (isDark()) Color(0xFF48484A) else Color(0xFFC7C7CC)),
    )
}

@Composable
fun BasicAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    properties: androidx.compose.ui.window.DialogProperties = androidx.compose.ui.window.DialogProperties(),
    content: @Composable () -> Unit,
) {
    androidx.compose.ui.window.Dialog(onDismissRequest = onDismissRequest, properties = properties) {
        Column(
            modifier = modifier
                .clip(RoundedCornerShape(20.dp))
                .background(if (isDark()) Color(0xFF1C1C1E) else Color.White)
                .padding(20.dp),
        ) {
            content()
        }
    }
}
