package com.wing.tree.bruni.daily.idioms.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wing.tree.bruni.daily.idioms.domain.model.Option
import com.wing.tree.bruni.daily.idioms.domain.model.Question
import kotlinx.collections.immutable.ImmutableList

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = false)
    override val index: Int,
    override val answer: Int?,
    @ColumnInfo(name = "correct_answer")
    override val correctAnswer: Int,
    @ColumnInfo(name = "is_solved")
    override val isSolved: Boolean,
    override val options: ImmutableList<Option>,
    override val text: String
) : Question()