package com.wing.tree.bruni.daily.idioms.data.dao

import androidx.room.*
import com.wing.tree.bruni.daily.idioms.data.entity.Question

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(questions: List<Question>)

    @Query("DELETE FROM question")
    suspend fun delete()

    @Query("SELECT * FROM question")
    suspend fun allQuestions(): List<Question>

    @Transaction
    suspend fun deleteAndInsert(questions: List<Question>) {
        delete()
        insert(questions)
    }
}