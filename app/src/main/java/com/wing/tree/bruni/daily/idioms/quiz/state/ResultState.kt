package com.wing.tree.bruni.daily.idioms.quiz.state

import com.wing.tree.bruni.daily.idioms.quiz.model.Question

data class ResultState(
    val index: Int,
    val question: Question
) {
    val answer = question.answer
    val correctAnswer = question.correctAnswer
    val isCorrectAnswer = answer == correctAnswer
}