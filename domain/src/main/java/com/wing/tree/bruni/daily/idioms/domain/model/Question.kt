package com.wing.tree.bruni.daily.idioms.domain.model

abstract class Question {
    abstract val index: Int
    abstract val answer: Int?
    abstract val correctAnswer: Int
    abstract val isSolved: Boolean
    abstract val options: List<String>
    abstract val text: String
}