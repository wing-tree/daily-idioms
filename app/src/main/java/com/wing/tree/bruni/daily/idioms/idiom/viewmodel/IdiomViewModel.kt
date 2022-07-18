package com.wing.tree.bruni.daily.idioms.idiom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.wing.tree.bruni.daily.idioms.R
import com.wing.tree.bruni.daily.idioms.constant.Key
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.domain.repository.IdiomRepository
import com.wing.tree.bruni.daily.idioms.constant.Category
import com.wing.tree.bruni.daily.idioms.idiom.state.IdiomState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class IdiomViewModel @Inject constructor(
    private val repository: IdiomRepository,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {
    private val ioDispatcher = Dispatchers.IO

    private val category = savedStateHandle.get<Category>(Key.CATEGORY)

    internal val title = with(application) {
        when(category) {
            Category.All -> getString(R.string.all_idioms)
            Category.CivilServiceExamination -> getString(R.string.civil_service_examination_idioms)
            Category.My -> getString(R.string.my_idioms)
            Category.Sat -> getString(R.string.sat_idioms)
            else -> throw IllegalArgumentException("category :$category")
        }
    }

    private val idioms = when(category) {
        Category.All -> repository.allIdioms()
        Category.CivilServiceExamination -> repository.civilServiceExaminationIdioms()
        Category.My -> repository.myIdioms()
        Category.Sat -> repository.satIdioms()
        else -> throw IllegalArgumentException("category :$category")
    }

    val state: StateFlow<IdiomState> = idioms.map {
        try {
            IdiomState.Content(it)
        } catch(ioException: IOException) {
            IdiomState.Error(ioException)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = IdiomState.Loading
    )

    fun updateMy(idiom: Idiom) {
        viewModelScope.launch(ioDispatcher) {
            repository.updateMy(idiom.id, idiom.my.not())
        }
    }
}