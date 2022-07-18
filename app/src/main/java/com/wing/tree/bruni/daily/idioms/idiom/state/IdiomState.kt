package com.wing.tree.bruni.daily.idioms.idiom.state

import com.wing.tree.bruni.daily.idioms.domain.model.Idiom

sealed interface IdiomState {
    object Loading : IdiomState
    data class Content(val idioms: List<Idiom>) : IdiomState
    data class Error(val throwable: Throwable) : IdiomState
}

