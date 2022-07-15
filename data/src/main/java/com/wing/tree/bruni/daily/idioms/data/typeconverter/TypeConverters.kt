package com.wing.tree.bruni.daily.idioms.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverters {
    private val type = object : TypeToken<List<String>>() {  }.type

    @TypeConverter
    fun listToJson(value: List<String>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        return Gson().fromJson(value, type)
    }
}