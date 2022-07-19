package com.wing.tree.bruni.daily.idioms.domain.model

import kotlinx.collections.immutable.ImmutableList

abstract class Option {
    abstract val chineseCharacters: String
    abstract val chineseMeanings: ImmutableList<String>
    abstract val description: String
    abstract val koreanCharacters: String
}