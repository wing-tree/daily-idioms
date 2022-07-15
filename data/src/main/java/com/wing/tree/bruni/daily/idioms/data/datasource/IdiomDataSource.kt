package com.wing.tree.bruni.daily.idioms.data.datasource

import com.wing.tree.bruni.daily.idioms.data.entity.Idiom
import kotlinx.coroutines.flow.Flow

interface IdiomDataSource {
    fun allIdioms(): Flow<List<Idiom>>
    fun civilServiceExaminationIdioms(): Flow<List<Idiom>>
    fun myIdioms(): Flow<List<Idiom>>
    fun satIdioms(): Flow<List<Idiom>>
    fun updateMy(id: Int, my: Boolean)
}