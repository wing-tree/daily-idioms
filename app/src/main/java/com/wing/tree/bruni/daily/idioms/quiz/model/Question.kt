package com.wing.tree.bruni.daily.idioms.quiz.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import com.wing.tree.bruni.daily.idioms.domain.model.Question as DomainModel

data class Question(
    override val index: Int,
    override val answer: Int?,
    override val correctAnswer: Int,
    override val isSolved: Boolean,
    override val options: ImmutableList<Option>,
    override val text: String
) : DomainModel() {
    companion object {
        fun from(question: DomainModel) = Question(
            index = question.index,
            answer = question.answer,
            correctAnswer = question.correctAnswer,
            isSolved = question.isSolved,
            options = question.options.map { Option.from(it) }.toImmutableList(),
            text = question.text
        )
    }
}