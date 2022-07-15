package com.wing.tree.bruni.daily.idioms.domain.model

abstract class Idiom {
    abstract val id: Int
    abstract val category: Int
    abstract val chineseCharacters: String
    abstract val chineseMeanings: List<String>
    abstract val description: String
    abstract val koreanCharacters: String
    abstract val my: Boolean
}