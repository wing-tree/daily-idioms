package com.wing.tree.bruni.daily.idioms.data.repository

import com.wing.tree.bruni.daily.idioms.data.datasource.QuestionDataSource
import com.wing.tree.bruni.daily.idioms.data.mapper.QuestionMapper.toEntity
import com.wing.tree.bruni.daily.idioms.domain.model.Question
import com.wing.tree.bruni.daily.idioms.domain.repository.QuestionRepository
import javax.inject.Inject

class QuestionRepositoryImpl @Inject constructor(private val dataSource: QuestionDataSource) : QuestionRepository {
    override suspend fun allQuestions(): List<Question> {
        return dataSource.allQuestions()
    }

    override suspend fun delete() {
        dataSource.delete()
    }

    override suspend fun deleteAndInsert(questions: List<Question>) {
        dataSource.deleteAndInsert(questions.map { it.toEntity() })
    }
}