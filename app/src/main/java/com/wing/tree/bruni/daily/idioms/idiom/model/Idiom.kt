package com.wing.tree.bruni.daily.idioms.idiom.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom as DomainModel

data class Idiom(
    override val id: Int,
    override val category: Int,
    override val chineseCharacters: String,
    override val chineseMeanings: ImmutableList<String>,
    override val description: String,
    override val koreanCharacters: String,
    override val my: Boolean
) : DomainModel() {
    var isMyIdiom by mutableStateOf(my)

    companion object {
        fun from(idiom: DomainModel) = Idiom(
            id = idiom.id,
            category = idiom.category,
            chineseCharacters = idiom.chineseCharacters,
            chineseMeanings = idiom.chineseMeanings,
            description = idiom.description,
            koreanCharacters = idiom.koreanCharacters,
            my = idiom.my
        )
    }
}