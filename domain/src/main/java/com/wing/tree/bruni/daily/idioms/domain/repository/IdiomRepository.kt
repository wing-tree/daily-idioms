package com.wing.tree.bruni.daily.idioms.domain.repository

import com.wing.tree.bruni.daily.idioms.domain.model.Idiom

interface IdiomRepository {
    suspend fun allIdioms(): List<Idiom>
    suspend fun civilServiceExaminationIdioms(): List<Idiom>
    suspend fun myIdioms(): List<Idiom>
    suspend fun satIdioms(): List<Idiom>
    suspend fun updateMy(id: Int, my: Boolean)
}