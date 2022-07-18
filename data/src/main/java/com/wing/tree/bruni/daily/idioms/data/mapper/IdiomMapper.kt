package com.wing.tree.bruni.daily.idioms.data.mapper

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom as DomainModel
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom as Entity

internal object IdiomMapper {
    fun DomainModel.toEntity() = Entity(
        id = id,
        category = category,
        chineseCharacters = chineseCharacters,
        chineseMeanings = chineseMeanings.toImmutableList(),
        description = description,
        koreanCharacters = koreanCharacters,
        my = my
    )
}