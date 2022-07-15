package com.wing.tree.bruni.daily.idioms.data.repository

import com.wing.tree.bruni.daily.idioms.data.datasource.IdiomDataSource
import com.wing.tree.bruni.daily.idioms.domain.model.Idiom
import com.wing.tree.bruni.daily.idioms.domain.repository.IdiomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IdiomRepositoryImpl @Inject constructor(private val dataSource: IdiomDataSource) : IdiomRepository {
    override fun allIdioms(): Flow<List<Idiom>> {
        return dataSource.allIdioms()
    }

    override fun civilServiceExaminationIdioms(): Flow<List<Idiom>> {
        return dataSource.civilServiceExaminationIdioms()
    }

    override fun myIdioms(): Flow<List<Idiom>> {
        return dataSource.myIdioms()
    }

    override fun satIdioms(): Flow<List<Idiom>> {
        return dataSource.satIdioms()
    }

    override fun updateMy(id: Int, my: Boolean) {
        dataSource.updateMy(id, my)
    }
}