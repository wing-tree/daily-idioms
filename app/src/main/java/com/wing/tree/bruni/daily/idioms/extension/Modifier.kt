package com.wing.tree.bruni.daily.idioms.extension

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal fun Modifier.paddingStart(start: Dp) = padding(
    start = start,
    top = 0.dp,
    end = 0.dp,
    bottom = 0.dp
)