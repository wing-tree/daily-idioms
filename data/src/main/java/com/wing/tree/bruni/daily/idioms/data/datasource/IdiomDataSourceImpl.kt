package com.wing.tree.bruni.daily.idioms.data.datasource

import com.wing.tree.bruni.daily.idioms.data.database.Database
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom
import javax.inject.Inject

class IdiomDataSourceImpl @Inject constructor(database: Database) : IdiomDataSource {
    private val idiomDao = database.idiomDao()

    override suspend fun allIdioms(): List<Idiom> {
        return idiomDao.allIdioms()
    }

    override suspend fun civilServiceExaminationIdioms(): List<Idiom> {
        return idiomDao.civilServiceExaminationIdioms()
    }

    override suspend fun myIdioms(): List<Idiom> {
        return idiomDao.myIdioms()
    }

    override suspend fun satIdioms(): List<Idiom> {
        return idiomDao.satIdioms()
    }

    override suspend fun updateMy(id: Int, my: Boolean) {
        idiomDao.updateMy(id, my)
    }
}