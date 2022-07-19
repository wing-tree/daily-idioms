package com.wing.tree.bruni.daily.idioms.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.wing.tree.bruni.daily.idioms.data.constant.Category.BOTH
import com.wing.tree.bruni.daily.idioms.data.constant.Category.CIVIL_SERVICE_EXAMINATION
import com.wing.tree.bruni.daily.idioms.data.constant.Category.SAT
import com.wing.tree.bruni.daily.idioms.data.constant.TRUE
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom

@Dao
interface IdiomDao {
    @Query("SELECT * FROM idiom")
    suspend fun allIdioms(): List<Idiom>

    @Query("SELECT * FROM idiom WHERE category = $BOTH OR category = $CIVIL_SERVICE_EXAMINATION")
    suspend  fun civilServiceExaminationIdioms(): List<Idiom>

    @Query("SELECT * FROM idiom WHERE my = $TRUE")
    suspend fun myIdioms(): List<Idiom>

    @Query("SELECT * FROM idiom WHERE category = $BOTH OR category = $SAT")
    suspend fun satIdioms(): List<Idiom>

    @Query("UPDATE idiom SET my = :my WHERE id = :id")
    fun updateMy(id: Int, my: Boolean)
}