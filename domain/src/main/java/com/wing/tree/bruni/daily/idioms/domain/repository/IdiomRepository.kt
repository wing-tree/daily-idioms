package com.wing.tree.bruni.daily.idioms.domain.repository

import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import kotlinx.coroutines.flow.Flow

interface IdiomRepository {
    fun allIdioms(): Flow<List<Idiom>>
    fun civilServiceExaminationIdioms(): Flow<List<Idiom>>
    fun myIdioms(): Flow<List<Idiom>>
    fun satIdioms(): Flow<List<Idiom>>
    fun updateMy(id: Int, my: Boolean)
}