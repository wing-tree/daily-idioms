package com.wing.tree.bruni.daily.idioms.quiz.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState
import com.wing.tree.bruni.daily.idioms.quiz.viewmodel.QuizViewModel

@Composable
internal fun Quiz(state: QuizState, onDoneClick: (QuizState.Progress.Content) -> Unit, onBackPressed: () -> Unit) {
    when(state) {
        is QuizState.Progress -> Progress(
            modifier = Modifier.fillMaxSize(),
            progress = state,
            onBackPressed = onBackPressed,
            onDoneClick = onDoneClick
        )
        is QuizState.Result -> Unit
    }
}