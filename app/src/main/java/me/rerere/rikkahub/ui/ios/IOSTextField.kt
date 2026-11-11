package me.rerere.rikkahub.ui.ios

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * iOS 文本输入框：圆角灰底填充框。
 * 与 Material3 同名；支持 value 版与 state 版（TextFieldState）两种 API。
 * Material 参数（colors/prefix/suffix/supportingText）接受但按 iOS 渲染。
 */

@Composable
private fun iosFieldColor(): Color =
    if (isDark()) Color(0xFF2C2C2E) else Color(0xFFE9E9EA)

@Composable
private fun iosFieldTextStyle(base: TextStyle): TextStyle =
    base.copy(color = base.color.takeIf { it != Color.Unspecified } ?: MaterialTheme.colorScheme.onSurface)

@Composable
private fun iosFieldDecoration(
    valueEmpty: Boolean,
    label: (@Composable () -> Unit)?,
    placeholder: (@Composable () -> Unit)?,
    leadingIcon: (@Composable () -> Unit)?,
    trailingIcon: (@Composable () -> Unit)?,
    error: Boolean,
    innerTextField: @Composable () -> Unit,
) {
    Column {
        if (label != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
            ) {
                label()
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(if (error) MaterialTheme.colorScheme.error.copy(alpha = 0.12f) else iosFieldColor())
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (leadingIcon != null) {
                leadingIcon()
                Spacer(Modifier.width(8.dp))
            }
            Box(modifier = Modifier.weight(1f)) {
                if (valueEmpty && placeholder != null) {
                    placeholder()
                }
                innerTextField()
            }
            if (trailingIcon != null) {
                Spacer(Modifier.width(8.dp))
                trailingIcon()
            }
        }
    }
}

/** iOS 文本输入框（value 版，描边样式） */
@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape? = null,
    colors: Any? = null,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = iosFieldTextStyle(textStyle),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(if (isError) MaterialTheme.colorScheme.error else iosBlue()),
        decorationBox = { inner ->
            iosFieldDecoration(value.isEmpty(), label, placeholder, leadingIcon, trailingIcon, isError, inner)
        },
    )
}

/** iOS 文本输入框（state 版，描边样式） */
@Composable
fun OutlinedTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape? = null,
    colors: Any? = null,
) {
    val text = state.text.toString()
    BasicTextField(
        value = text,
        onValueChange = { new -> state.edit { replace(0, length, new) } },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = iosFieldTextStyle(textStyle),
        singleLine = lineLimits is TextFieldLineLimits.SingleLine,
        maxLines = when (lineLimits) {
            is TextFieldLineLimits.MultiLine -> lineLimits.maxHeightInLines
            else -> Int.MAX_VALUE
        },
        minLines = when (lineLimits) {
            is TextFieldLineLimits.MultiLine -> lineLimits.minHeightInLines
            else -> 1
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = iosKeyboardActions(onKeyboardAction),
        interactionSource = interactionSource,
        cursorBrush = SolidColor(if (isError) MaterialTheme.colorScheme.error else iosBlue()),
        decorationBox = { inner ->
            iosFieldDecoration(text.isEmpty(), label, placeholder, leadingIcon, trailingIcon, isError, inner)
        },
    )
}

/** iOS 文本输入框（value 版，填充样式） */
@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape? = null,
    colors: Any? = null,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = iosFieldTextStyle(textStyle),
        singleLine = singleLine,
        maxLines = if (singleLine) 1 else maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(if (isError) MaterialTheme.colorScheme.error else iosBlue()),
        decorationBox = { inner ->
            iosFieldDecoration(value.isEmpty(), label, placeholder, leadingIcon, trailingIcon, isError, inner)
        },
    )
}

/** iOS 文本输入框（state 版，填充样式） */
@Composable
fun TextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    prefix: (@Composable () -> Unit)? = null,
    suffix: (@Composable () -> Unit)? = null,
    supportingText: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape? = null,
    colors: Any? = null,
) {
    val text = state.text.toString()
    BasicTextField(
        value = text,
        onValueChange = { new -> state.edit { replace(0, length, new) } },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = iosFieldTextStyle(textStyle),
        singleLine = lineLimits is TextFieldLineLimits.SingleLine,
        maxLines = when (lineLimits) {
            is TextFieldLineLimits.MultiLine -> lineLimits.maxHeightInLines
            else -> Int.MAX_VALUE
        },
        minLines = when (lineLimits) {
            is TextFieldLineLimits.MultiLine -> lineLimits.minHeightInLines
            else -> 1
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = iosKeyboardActions(onKeyboardAction),
        interactionSource = interactionSource,
        cursorBrush = SolidColor(if (isError) MaterialTheme.colorScheme.error else iosBlue()),
        decorationBox = { inner ->
            iosFieldDecoration(text.isEmpty(), label, placeholder, leadingIcon, trailingIcon, isError, inner)
        },
    )
}

private fun iosKeyboardActions(handler: KeyboardActionHandler?): KeyboardActions = KeyboardActions(
    onSend = { handler?.onKeyboardAction { } },
    onDone = { handler?.onKeyboardAction { } },
    onNext = { handler?.onKeyboardAction { } },
    onPrevious = { handler?.onKeyboardAction { } },
    onGo = { handler?.onKeyboardAction { } },
    onSearch = { handler?.onKeyboardAction { } },
)
