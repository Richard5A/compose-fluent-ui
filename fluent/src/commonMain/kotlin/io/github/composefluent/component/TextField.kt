package io.github.composefluent.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import io.github.composefluent.FluentTheme
import io.github.composefluent.LocalContentAlpha
import io.github.composefluent.LocalContentColor
import io.github.composefluent.LocalTextStyle
import io.github.composefluent.background.BackgroundSizing
import io.github.composefluent.background.Layer
import io.github.composefluent.layout.alignLast
import io.github.composefluent.scheme.PentaVisualScheme
import io.github.composefluent.scheme.collectVisualState

@Composable
fun TextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = Int.MAX_VALUE,
    header: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailing: (@Composable RowScope.() -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    isClearable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColorScheme = TextFieldDefaults.defaultTextFieldColors(),
    shape: Shape = FluentTheme.shapes.control
) {
    val color = colors.schemeFor(interactionSource.collectVisualState(!enabled, focusFirst = true))
    HeaderContainer(header = header, modifier = modifier) {
        BasicTextField(
            modifier = modifier.textFieldModifier(shape),
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(color = color.contentColor),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            keyboardActions = keyboardActions,
            cursorBrush = color.cursorBrush,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    color = color,
                    interactionSource = interactionSource,
                    innerTextField = innerTextField,
                    value = value.text,
                    enabled = enabled,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    onClearClick = if (isClearable) {
                        { onValueChange(TextFieldValue("")) }
                    } else {
                        null
                    },
                    trailing = trailing,
                    shape = shape
                )
            }
        )
    }
}

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = Int.MAX_VALUE,
    header: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailing: (@Composable RowScope.() -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    isClearable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColorScheme = TextFieldDefaults.defaultTextFieldColors(),
    shape: Shape = FluentTheme.shapes.control
) {
    val color = colors.schemeFor(interactionSource.collectVisualState(!enabled, focusFirst = true))
    HeaderContainer(header = header, modifier = modifier) {
        BasicTextField(
            modifier = modifier.textFieldModifier(shape),
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(color = color.contentColor),
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            maxLines = maxLines,
            keyboardActions = keyboardActions,
            cursorBrush = color.cursorBrush,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    color = color,
                    interactionSource = interactionSource,
                    innerTextField = innerTextField,
                    value = value,
                    enabled = enabled,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    onClearClick = if (isClearable) {
                        { onValueChange("") }
                    } else {
                        null
                    },
                    trailing = trailing,
                    shape = shape
                )
            }
        )
    }
}

@Composable
fun TextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    inputTransformation: InputTransformation? = null,
    outputTransformation: OutputTransformation? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    header: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailing: (@Composable RowScope.() -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    isClearable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColorScheme = TextFieldDefaults.defaultTextFieldColors(),
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    scrollState: ScrollState = rememberScrollState(),
    shape: Shape = FluentTheme.shapes.control
) {
    val color = colors.schemeFor(interactionSource.collectVisualState(!enabled, focusFirst = true))
    HeaderContainer(header = header, modifier = modifier) {
        BasicTextField(
            modifier = modifier.textFieldModifier(shape),
            state = state,
            textStyle = LocalTextStyle.current.copy(color = color.contentColor),
            enabled = enabled,
            readOnly = readOnly,
            onTextLayout = onTextLayout,
            lineLimits = lineLimits,
            onKeyboardAction = onKeyboardAction,
            inputTransformation = inputTransformation,
            outputTransformation = outputTransformation,
            scrollState = scrollState,
            cursorBrush = color.cursorBrush,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            decorator = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    state = state,
                    color = color,
                    interactionSource = interactionSource,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    isClearable = isClearable,
                    outputTransformation = outputTransformation,
                    trailing = trailing,
                    shape = shape
                )
            }
        )
    }
}

@Composable
fun SecureTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = SecureTextFieldKeyboardOptions,
    onKeyboardAction: KeyboardActionHandler? = null,
    inputTransformation: InputTransformation? = null,
    textObfuscationMode: TextObfuscationMode = TextObfuscationMode.RevealLastTyped,
    textObfuscationCharacter: Char = DefaultObfuscationCharacter,
    header: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailing: (@Composable RowScope.() -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    isClearable: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColorScheme = TextFieldDefaults.defaultTextFieldColors(),
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    shape: Shape = FluentTheme.shapes.control
) {
    val color = colors.schemeFor(interactionSource.collectVisualState(!enabled, focusFirst = true))
    HeaderContainer(header = header, modifier = modifier) {
        BasicSecureTextField(
            modifier = modifier.textFieldModifier(shape),
            state = state,
            textStyle = LocalTextStyle.current.copy(color = color.contentColor),
            enabled = enabled,
            onTextLayout = onTextLayout,
            onKeyboardAction = onKeyboardAction,
            inputTransformation = inputTransformation,
            textObfuscationMode = textObfuscationMode,
            textObfuscationCharacter = textObfuscationCharacter,
            cursorBrush = color.cursorBrush,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            decorator = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    state = state,
                    color = color,
                    interactionSource = interactionSource,
                    innerTextField = innerTextField,
                    enabled = enabled,
                    placeholder = placeholder,
                    leadingIcon = leadingIcon,
                    isClearable = isClearable,
                    outputTransformation = null,
                    trailing = trailing,
                    shape = shape
                )
            }
        )
    }
}

object TextFieldDefaults {

    @Stable
    @Composable
    fun defaultTextFieldColors(
        default: TextFieldColor = TextFieldColor(
            fillColor = FluentTheme.colors.control.default,
            contentColor = FluentTheme.colors.text.text.primary,
            placeholderColor = FluentTheme.colors.text.text.secondary,
            bottomLineFillColor = FluentTheme.colors.stroke.controlStrong.default,
            borderBrush = FluentTheme.colors.borders.textControl,
            cursorBrush = SolidColor(FluentTheme.colors.text.text.primary)
        ),
        focused: TextFieldColor = default.copy(
            fillColor = FluentTheme.colors.control.inputActive,
            bottomLineFillColor = FluentTheme.colors.fillAccent.default,
            borderBrush = SolidColor(FluentTheme.colors.stroke.control.default)
        ),
        hovered: TextFieldColor = default.copy(
            fillColor = FluentTheme.colors.control.secondary
        ),
        pressed: TextFieldColor = default.copy(
            fillColor = FluentTheme.colors.control.inputActive,
            borderBrush = SolidColor(FluentTheme.colors.stroke.control.default)
        ),
        disabled: TextFieldColor = default.copy(
            contentColor = FluentTheme.colors.text.text.disabled,
            placeholderColor = FluentTheme.colors.text.text.disabled,
            bottomLineFillColor = Color.Transparent,
        )
    ) = TextFieldColorScheme(
        default = default,
        focused = focused,
        hovered = hovered,
        pressed = pressed,
        disabled = disabled
    )

    @Deprecated(
        "Use DecorationBox instead", ReplaceWith(
            "DecorationBox(" +
                    "value = value," +
                    "interactionSource = interactionSource," +
                    "enabled = enabled," +
                    "color = color," +
                    "modifier = modifier," +
                    "placeholder = placeholder," +
                    "innerTextField = innerTextField," +
                    "leadingIcon = null" +
                    ")"
        )
    )
    @Composable
    fun DecorationBox(
        value: String,
        interactionSource: MutableInteractionSource,
        enabled: Boolean,
        color: TextFieldColor,
        modifier: Modifier = Modifier.drawBottomLine(enabled, color, interactionSource),
        placeholder: (@Composable () -> Unit)?,
        innerTextField: @Composable () -> Unit,
    ) = DecorationBox(
        value = value,
        interactionSource = interactionSource,
        enabled = enabled,
        color = color,
        modifier = modifier,
        placeholder = placeholder,
        innerTextField = innerTextField,
        leadingIcon = null,
        onClearClick = null,
        trailing = null,
        shape = FluentTheme.shapes.control
    )

    @Composable
    fun DecorationBox(
        state: TextFieldState,
        isClearable: Boolean,
        outputTransformation: OutputTransformation?,
        interactionSource: MutableInteractionSource,
        enabled: Boolean,
        color: TextFieldColor,
        modifier: Modifier = Modifier.drawBottomLine(enabled, color, interactionSource),
        shape: Shape,
        placeholder: (@Composable () -> Unit)?,
        leadingIcon: (@Composable () -> Unit)?,
        trailing: (@Composable RowScope.() -> Unit)?,
        innerTextField: @Composable () -> Unit,
    ) {
        val visualText = if (outputTransformation == null) state.text
        else {
            lateinit var buffer: TextFieldBuffer
            state.edit { buffer = this }
            with(outputTransformation) { buffer.transformOutput() }
            buffer.asCharSequence()
        }
        DecorationBox(
            color = color,
            interactionSource = interactionSource,
            innerTextField = innerTextField,
            value = visualText,
            enabled = enabled,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            onClearClick = if (isClearable) {
                { state.clearText() }
            } else {
                null
            },
            trailing = trailing,
            shape = shape,
            modifier = modifier
        )
    }

    @Composable
    fun DecorationBox(
        value: CharSequence,
        interactionSource: MutableInteractionSource,
        enabled: Boolean,
        color: TextFieldColor,
        modifier: Modifier = Modifier.drawBottomLine(enabled, color, interactionSource),
        shape: Shape,
        onClearClick: (() -> Unit)? = null,
        placeholder: (@Composable () -> Unit)?,
        leadingIcon: (@Composable () -> Unit)?,
        trailing: (@Composable RowScope.() -> Unit)?,
        innerTextField: @Composable () -> Unit,
    ) {

        Layer(
            modifier = modifier.hoverable(interactionSource),
            shape = shape,
            color = color.fillColor,
            border = BorderStroke(1.dp, color.borderBrush),
            backgroundSizing = BackgroundSizing.OuterBorderEdge
        ) {
            Row(
                horizontalArrangement = TextFieldContentArrangement,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            ) {
                if (leadingIcon != null) {
                    Box(modifier = Modifier.padding(start = 16.dp)) {
                        leadingIcon()
                    }
                }
                Box(
                    modifier = Modifier.weight(1f, fill = false).padding(horizontal = 12.dp),
                    Alignment.CenterStart
                ) {
                    innerTextField()
                    if (value.isEmpty() && placeholder != null) {
                        CompositionLocalProvider(
                            LocalContentColor provides color.placeholderColor,
                            LocalTextStyle provides LocalTextStyle.current.copy(color = color.placeholderColor)
                        ) {
                            placeholder()
                        }
                    }
                }
                val isFocused = interactionSource.collectIsFocusedAsState()
                val hasClearButton = onClearClick != null && isFocused.value && value.isNotEmpty()
                if (trailing != null || hasClearButton) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        if (hasClearButton) {
                            TextBoxButton(
                                enabled = enabled,
                                onClick = onClearClick
                            ) { TextBoxButtonDefaults.ClearIcon() }
                        }
                        trailing?.invoke(this)
                    }
                }
            }
        }
    }
}

typealias TextFieldColorScheme = PentaVisualScheme<TextFieldColor>

@Immutable
data class TextFieldColor(
    val fillColor: Color,
    val contentColor: Color,
    val placeholderColor: Color,
    val bottomLineFillColor: Color,
    val borderBrush: Brush,
    val cursorBrush: Brush
)

@Composable
private fun HeaderContainer(
    header: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        if (header != null) {
            CompositionLocalProvider(
                LocalTextStyle provides FluentTheme.typography.body,
                LocalContentColor provides FluentTheme.colors.text.text.primary,
                LocalContentAlpha provides FluentTheme.colors.text.text.primary.alpha
            ) {
                header()
            }
            Spacer(Modifier.height(8.dp))
        }
        content()
    }
}

@Composable
private fun Modifier.drawBottomLine(
    enabled: Boolean,
    color: TextFieldColor,
    interactionSource: MutableInteractionSource
): Modifier {
    val isFocused by interactionSource.collectIsFocusedAsState()
    return if (enabled) {
        val height by rememberUpdatedState(with(LocalDensity.current) {
            (if (isFocused) 2.dp else 1.dp).toPx()
        })
        val fillColor by rememberUpdatedState(color.bottomLineFillColor)
        drawWithContent {
            drawContent()
            drawRect(
                color = fillColor,
                topLeft = Offset(0f, size.height - height),
                size = Size(size.width, height)
            )
        }
    } else this
}

@Stable
internal fun Modifier.textFieldModifier(shape: Shape) =
    defaultMinSize(64.dp, 32.dp).clip(shape)

private val TextFieldContentArrangement = Arrangement.alignLast(Arrangement.Start, Alignment.End)

private val SecureTextFieldKeyboardOptions =
    KeyboardOptions(autoCorrectEnabled = false, keyboardType = KeyboardType.Password)

private const val DefaultObfuscationCharacter: Char = '\u2022'
