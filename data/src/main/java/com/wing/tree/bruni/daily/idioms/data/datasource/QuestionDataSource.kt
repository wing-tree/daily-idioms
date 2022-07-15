package com.wing.tree.bruni.daily.idioms.data.datasource

import com.wing.tree.bruni.daily.idioms.data.entity.Question

interface QuestionDataSource {
    suspend fun allQuestions(): List<Question>
    suspend fun delete()
    suspend fun deleteAndInsert(questions: List<Question>)
}