package com.wing.tree.bruni.daily.idioms.data.datasource

import com.wing.tree.bruni.daily.idioms.data.entity.Idiom

interface IdiomDataSource {
    suspend fun allIdioms(): List<Idiom>
    suspend fun civilServiceExaminationIdioms(): List<Idiom>
    suspend fun myIdioms(): List<Idiom>
    suspend fun satIdioms(): List<Idiom>
    suspend fun updateMy(id: Int, my: Boolean)
}