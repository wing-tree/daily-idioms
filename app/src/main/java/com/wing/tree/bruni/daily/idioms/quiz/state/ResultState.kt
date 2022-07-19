package com.wing.tree.bruni.daily.idioms.quiz.state

import com.wing.tree.bruni.daily.idioms.quiz.model.Question

data class ResultState(val question: Question) {
    val index = question.index

    val answer = question.answer
    val isCorrectAnswer = answer == question.correctAnswer
}