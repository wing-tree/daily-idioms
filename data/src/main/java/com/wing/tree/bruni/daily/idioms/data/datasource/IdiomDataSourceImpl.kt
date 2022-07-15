package com.wing.tree.bruni.daily.idioms.data.datasource

import com.wing.tree.bruni.daily.idioms.data.database.Database
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IdiomDataSourceImpl @Inject constructor(database: Database) : IdiomDataSource {
    private val idiomDao = database.idiomDao()

    override fun allIdioms(): Flow<List<Idiom>> {
        return idiomDao.allIdioms()
    }

    override fun civilServiceExaminationIdioms(): Flow<List<Idiom>> {
        return idiomDao.civilServiceExaminationIdioms()
    }

    override fun myIdioms(): Flow<List<Idiom>> {
        return idiomDao.myIdioms()
    }

    override fun satIdioms(): Flow<List<Idiom>> {
        return idiomDao.satIdioms()
    }

    override fun updateMy(id: Int, my: Boolean) {
        idiomDao.updateMy(id, my)
    }
}