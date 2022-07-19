package com.wing.tree.bruni.daily.idioms.domain.model

import kotlinx.collections.immutable.ImmutableList

abstract class Question {
    abstract val index: Int
    abstract val answer: Int?
    abstract val correctAnswer: Int
    abstract val isSolved: Boolean
    abstract val options: ImmutableList<Option>
    abstract val text: String
}