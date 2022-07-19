package com.wing.tree.bruni.daily.idioms.data.mapper

import com.wing.tree.bruni.daily.idioms.domain.model.Question as DomainModel
import com.wing.tree.bruni.daily.idioms.data.entity.Question as Entity

internal object QuestionMapper {
    fun DomainModel.toEntity() = Entity(
        index = index,
        answer = answer,
        correctAnswer = correctAnswer,
        isSolved = isSolved,
        options = options,
        text = text
    )
}