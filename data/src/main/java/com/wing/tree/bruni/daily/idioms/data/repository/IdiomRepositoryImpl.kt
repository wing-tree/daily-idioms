package com.wing.tree.bruni.daily.idioms.data.repository

import com.wing.tree.bruni.daily.idioms.data.datasource.IdiomDataSource
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.domain.repository.IdiomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IdiomRepositoryImpl @Inject constructor(private val dataSource: IdiomDataSource) : IdiomRepository {
    override suspend fun allIdioms(): List<Idiom> {
        return dataSource.allIdioms()
    }

    override suspend fun civilServiceExaminationIdioms(): List<Idiom> {
        return dataSource.civilServiceExaminationIdioms()
    }

    override suspend fun myIdioms(): List<Idiom> {
        return dataSource.myIdioms()
    }

    override suspend fun satIdioms(): List<Idiom> {
        return dataSource.satIdioms()
    }

    override suspend fun updateMy(id: Int, my: Boolean) {
        dataSource.updateMy(id, my)
    }
}