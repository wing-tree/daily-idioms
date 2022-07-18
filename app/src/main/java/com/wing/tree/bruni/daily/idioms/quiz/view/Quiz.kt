package com.wing.tree.bruni.daily.idioms.quiz.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wing.tree.bruni.daily.idioms.quiz.state.QuizState

@Composable
internal fun Quiz(modifier: Modifier, state: QuizState, onDoneClick: (QuizState.Progress.Content) -> Unit, onBackPressed: () -> Unit) {
    Surface(modifier = modifier) {
        when (state) {
            is QuizState.Progress -> Progress(
                modifier = Modifier.fillMaxSize(),
                progress = state,
                onBackPressed = onBackPressed,
                onDoneClick = onDoneClick
            )
            is QuizState.Result -> Result(
                modifier = Modifier.fillMaxSize(),
                result = state
            )
            is QuizState.Commentary -> Unit
        }
    }
}