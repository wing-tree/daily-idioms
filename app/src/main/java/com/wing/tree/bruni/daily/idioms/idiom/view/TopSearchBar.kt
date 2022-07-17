package com.wing.tree.bruni.daily.idioms.idiom.view

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wing.tree.bruni.daily.idioms.R
import com.wing.tree.bruni.daily.idioms.constant.EMPTY
import com.wing.tree.bruni.daily.idioms.constant.ONE
import com.wing.tree.bruni.daily.idioms.constant.Keyboard
import com.wing.tree.bruni.daily.idioms.extension.paddingStart
import com.wing.tree.bruni.daily.idioms.idiom.state.IdiomState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun TopSearchBar(
    state: IdiomState,
    onQueryTextChanged: (String) -> Unit,
    onTextFieldVisibilityChanged: (isTextFieldVisible: Boolean) -> Unit,
    onBackPressed: () -> Unit
) {
    val delayMillis = 200
    val focusRequester = remember { FocusRequester() }
    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
    val keyboardState by keyboardAsState()
    var isTextFieldVisible by mutableStateOf(false)
    var isFocused by mutableStateOf(false)
    var queryText by mutableStateOf(EMPTY)

    fun setTextFieldVisible(value: Boolean) {
        if (value) {
            queryText = EMPTY
        }

        isTextFieldVisible = value
        onTextFieldVisibilityChanged(isTextFieldVisible)
    }

    BackHandler(true) {
        if (isTextFieldVisible) {
            when(keyboardState) {
                Keyboard.Hidden -> setTextFieldVisible(false)
                else -> return@BackHandler
            }
        } else {
            onBackPressed()
        }
    }

    SmallTopAppBar(
        title = {
            AnimatedVisibility(
                visible = isTextFieldVisible.not(),
                enter = expandHorizontally(tween(delayMillis = delayMillis)) +
                        fadeIn(tween(delayMillis = delayMillis)),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                when (state) {
                    is IdiomState.Content -> Text(
                        text = state.title,
                        overflow = TextOverflow.Visible,
                        maxLines = ONE
                    )
                    else -> Unit
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    if (isTextFieldVisible) {
                        setTextFieldVisible(false)
                    } else {
                        onBackPressed()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            val localViewConfiguration = LocalViewConfiguration.current
            val start = localViewConfiguration.minimumTouchTargetSize.width
            val trailingIconVisible = isFocused.and(queryText.isNotEmpty())

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = isTextFieldVisible,
                    enter = expandHorizontally() + fadeIn(),
                    exit = fadeOut() + shrinkHorizontally()
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1.0F)
                            .paddingStart(start)
                            .onFocusChanged { focusState ->
                                isFocused = focusState.isFocused
                            }
                            .focusRequester(focusRequester),
                        value = queryText,
                        onValueChange = {
                            queryText = it
                            onQueryTextChanged.invoke(queryText)
                        },
                        placeholder = { Text(text = stringResource(id = R.string.search)) },
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            cursorColor = LocalContentColor.current.copy(alpha = LocalContentColor.current.alpha),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = trailingIconVisible,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                IconButton(onClick = { queryText = EMPTY }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = null
                                    )
                                }
                            }
                        },
                        maxLines = ONE,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                KeyboardOptions.Default
                                localSoftwareKeyboardController?.hide()
                            }
                        ),
                    )

                    LaunchedEffect(isTextFieldVisible) {
                        focusRequester.requestFocus()
                    }
                }

                AnimatedVisibility(
                    visible = isTextFieldVisible.not(),
                    enter = expandHorizontally(tween(delayMillis = delayMillis)) +
                            fadeIn(tween(delayMillis = delayMillis)),
                    exit = fadeOut() + shrinkHorizontally()
                ) {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { setTextFieldVisible(true) }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_skip_96px),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Hidden) }
    val localView = LocalView.current

    DisposableEffect(localView) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()

            localView.getWindowVisibleDisplayFrame(rect)

            val height = localView.rootView.height
            val keyboardHeight = height - rect.bottom

            keyboardState.value = if (keyboardHeight > height * 0.15F) {
                Keyboard.Shown
            } else {
                Keyboard.Hidden
            }
        }

        localView.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            localView.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}