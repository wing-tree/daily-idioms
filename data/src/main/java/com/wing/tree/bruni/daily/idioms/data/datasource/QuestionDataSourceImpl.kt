package com.wing.tree.bruni.daily.idioms.data.datasource

import com.wing.tree.bruni.daily.idioms.data.database.Database
import com.wing.tree.bruni.daily.idioms.data.entity.Question
import javax.inject.Inject

class QuestionDataSourceImpl @Inject constructor(database: Database) : QuestionDataSource {
    private val quizDao = database.questionDao()

    override suspend fun allQuestions(): List<Question> {
        return quizDao.allQuestions()
    }

    override suspend fun delete() {
        quizDao.delete()
    }

    override suspend fun deleteAndInsert(questions: List<Question>) {
        quizDao.deleteAndInsert(questions)
    }
}