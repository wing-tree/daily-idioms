package com.wing.tree.bruni.daily.idioms.quiz.state

import com.wing.tree.bruni.daily.idioms.quiz.model.Question

data class CommentaryState(
    val question: Question,
    val isPreviousVisible: Boolean,
    val isHomeVisible: Boolean
) {
    val index: Int = question.index
}