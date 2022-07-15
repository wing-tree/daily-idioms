package com.wing.tree.bruni.daily.idioms.quiz.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

sealed interface QuizState {
    sealed interface Progress : QuizState {
        object Loading : Progress
        data class Content(
            val questionsState: List<QuestionState>
        ) : Progress {
            val count = questionsState.count()
            val currentQuestionState: QuestionState get() = questionsState[currentIndex]
            var currentIndex by mutableStateOf(0)
        }

        data class Error(val throwable: Throwable): Progress
    }

    sealed interface Result : QuizState {
        object Loading : Result
        object Content : Result
        data class Error(val throwable: Throwable): Result
    }
}