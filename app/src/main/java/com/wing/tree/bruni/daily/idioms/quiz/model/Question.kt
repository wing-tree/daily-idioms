package com.wing.tree.bruni.daily.idioms.quiz.model

import com.wing.tree.bruni.daily.idioms.domain.model.Question as DomainModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class Question(
    override val index: Int,
    override val answer: Int?,
    override val correctAnswer: Int,
    override val isSolved: Boolean,
    override val options: ImmutableList<String>,
    override val text: String
) : DomainModel() {
    companion object {
        fun from(question: DomainModel) = Question(
            index = question.index,
            answer = question.answer,
            correctAnswer = question.correctAnswer,
            isSolved = question.isSolved,
            options = question.options.toImmutableList(),
            text = question.text
        )
    }
}