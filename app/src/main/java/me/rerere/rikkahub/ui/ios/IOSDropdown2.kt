package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

/**
 * iOS 下拉选择框。
 */

interface ExposedDropdownMenuBoxScope {
    fun Modifier.menuAnchor(type: Any? = null, enabled: Boolean = true): Modifier
    fun Modifier.exposedDropdownSize(matchTextFieldWidth: Boolean = true): Modifier
}

@Composable
fun ExposedDropdownMenuBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ExposedDropdownMenuBoxScope.() -> Unit,
) {
    val scope = object : ExposedDropdownMenuBoxScope {
        override fun Modifier.menuAnchor(type: Any?, enabled: Boolean): Modifier = this
        override fun Modifier.exposedDropdownSize(matchTextFieldWidth: Boolean): Modifier = this
    }
    Box(modifier = modifier) {
        content(scope)
    }
}

@Composable
fun ExposedDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: androidx.compose.foundation.ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit,
) {
    if (expanded) {
        Popup(
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true),
        ) {
            Column(
                modifier = modifier
                    .widthIn(min = 120.dp, max = 320.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(if (isDark()) Color(0xFF1C1C1E) else Color.White)
                    .padding(vertical = 8.dp),
            ) {
                content()
            }
        }
    }
}
