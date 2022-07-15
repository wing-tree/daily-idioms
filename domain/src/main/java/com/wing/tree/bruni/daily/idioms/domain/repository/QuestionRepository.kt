package com.wing.tree.bruni.daily.idioms.domain.repository

import com.wing.tree.bruni.daily.idioms.domain.model.Question

interface QuestionRepository {
    suspend fun allQuestions(): List<Question>
    suspend fun delete()
    suspend fun deleteAndInsert(questions: List<Question>)
}