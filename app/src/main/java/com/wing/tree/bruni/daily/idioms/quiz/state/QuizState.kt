package com.wing.tree.bruni.daily.idioms.quiz.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

sealed interface QuizState {
    sealed interface Progress : QuizState {
        object Loading : Progress
        data class Content(val questionsState: List<QuestionState>) : Progress {
            val count = questionsState.count()
            val currentQuestionState: QuestionState get() = questionsState[currentIndex]
            var currentIndex by mutableStateOf(0)
        }

        data class Error(val throwable: Throwable): Progress
    }

    sealed interface Result : QuizState {
        object Loading : Result
        data class Content(
            val resultsState: List<ResultState>,
            val score: Float
        ) : Result
        data class Error(val throwable: Throwable): Result
    }

    sealed interface Commentary : QuizState {
        object Loading : Commentary
        data class Content(val commentariesState: List<CommentaryState>) : Commentary {
            val count = commentariesState.count()
            val currentCommentaryState: CommentaryState get() = commentariesState[currentIndex]
            var currentIndex by mutableStateOf(0)
        }

        data class Error(val throwable: Throwable): Commentary
    }
}