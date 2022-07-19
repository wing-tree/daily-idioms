package com.wing.tree.bruni.daily.idioms.quiz.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import kotlinx.collections.immutable.ImmutableList
import com.wing.tree.bruni.daily.idioms.domain.model.Option as DomainModel

data class Option(
    override val chineseCharacters: String,
    override val chineseMeanings: ImmutableList<String>,
    override val description: String,
    override val koreanCharacters: String
) : DomainModel() {
    var expanded by mutableStateOf(false)

    companion object {
        fun from(option: DomainModel) = Option(
            chineseCharacters = option.chineseCharacters,
            chineseMeanings = option.chineseMeanings,
            description = option.description,
            koreanCharacters = option.koreanCharacters
        )

        fun from(idiom: Idiom) = Option(
            chineseCharacters = idiom.chineseCharacters,
            chineseMeanings = idiom.chineseMeanings,
            description = idiom.description,
            koreanCharacters = idiom.koreanCharacters
        )
    }
}
