package com.wing.tree.bruni.daily.idioms.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom

@Entity(tableName = "idiom")
data class Idiom(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val category: Int,
    override val chineseCharacters: String,
    override val chineseMeanings: List<String>,
    override val description: String,
    override val koreanCharacters: String,
    override val my: Boolean
) : Idiom()