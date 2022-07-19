package com.wing.tree.bruni.daily.idioms.quiz.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wing.tree.bruni.daily.idioms.domain.extension.notNull
import com.wing.tree.bruni.daily.idioms.quiz.model.Question

data class QuestionState(
    val question: Question,
    val isPreviousVisible: Boolean,
    val isDoneVisible: Boolean
) {
    val index: Int = question.index
    val isNextEnabled: Boolean get() = answer.notNull
    var answer by mutableStateOf(question.answer)
}