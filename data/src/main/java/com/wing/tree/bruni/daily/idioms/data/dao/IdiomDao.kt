package com.wing.tree.bruni.daily.idioms.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.wing.tree.bruni.daily.idioms.data.constant.Category.BOTH
import com.wing.tree.bruni.daily.idioms.data.constant.Category.CIVIL_SERVICE_EXAMINATION
import com.wing.tree.bruni.daily.idioms.data.constant.Category.SAT
import com.wing.tree.bruni.daily.idioms.data.constant.TRUE
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom
import kotlinx.coroutines.flow.Flow

@Dao
interface IdiomDao {
    @Query("SELECT * FROM idiom")
    fun allIdioms(): Flow<List<Idiom>>

    @Query("SELECT * FROM idiom WHERE category = $BOTH OR category = $CIVIL_SERVICE_EXAMINATION")
    fun civilServiceExaminationIdioms(): Flow<List<Idiom>>

    @Query("SELECT * FROM idiom WHERE my = $TRUE")
    fun myIdioms(): Flow<List<Idiom>>

    @Query("SELECT * FROM idiom WHERE category = $BOTH OR category = $SAT")
    fun satIdioms(): Flow<List<Idiom>>

    @Query("UPDATE idiom SET my = :my WHERE id = :id")
    fun updateMy(id: Int, my: Boolean)
}