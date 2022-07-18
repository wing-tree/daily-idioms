package com.wing.tree.bruni.daily.idioms.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class TypeConverters {
    @Suppress("HasPlatformType")
    @TypeConverter
    fun idiomToJson(value: Idiom) = Gson().toJson(value)

    @TypeConverter
    fun jsonToIdiom(value: String): Idiom {
        return Gson().fromJson(value, Idiom::class.java)
    }

    @Suppress("HasPlatformType")
    @TypeConverter
    fun stringsToJson(value: List<String>) = Gson().toJson(value.toList())

    @TypeConverter
    fun jsonToStrings(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {  }.type

        return Gson().fromJson(value, type)
    }

    @Suppress("HasPlatformType")
    @TypeConverter
    fun immutableIdiomsToJson(value: ImmutableList<Idiom>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToImmutableIdioms(value: String): ImmutableList<Idiom> {
        val type = object : TypeToken<List<Idiom>>() {  }.type

        return Gson().fromJson<List<Idiom>>(value, type).toImmutableList()
    }
}