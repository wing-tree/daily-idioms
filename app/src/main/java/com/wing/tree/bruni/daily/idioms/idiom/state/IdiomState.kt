package com.wing.tree.bruni.daily.idioms.idiom.state

import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import kotlinx.collections.immutable.ImmutableList

sealed interface IdiomState {
    object Loading : IdiomState
    data class Content(val idioms: ImmutableList<Idiom>) : IdiomState
    data class Error(val throwable: Throwable) : IdiomState
}

