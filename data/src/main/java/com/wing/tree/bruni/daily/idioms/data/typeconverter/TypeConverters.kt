package com.wing.tree.bruni.daily.idioms.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class TypeConverters {
    private val type = object : TypeToken<List<String>>() {  }.type

    @Suppress("HasPlatformType")
    @TypeConverter
    fun listToJson(value: List<String>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        return Gson().fromJson(value, type)
    }

    @Suppress("HasPlatformType")
    @TypeConverter
    fun immutableListToJson(value: ImmutableList<String>) = Gson().toJson(value.toList())

    @TypeConverter
    fun jsonToImmutableList(value: String): ImmutableList<String> {
        return Gson().fromJson<List<String>>(value, type).toImmutableList()
    }
}