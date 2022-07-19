package com.wing.tree.bruni.daily.idioms.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.wing.tree.bruni.daily.idioms.domain.model.Option
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.lang.reflect.Type

class TypeConverters {
    @Suppress("HasPlatformType")
    @TypeConverter
    fun immutableStringsToJson(value: ImmutableList<String>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToImmutableStrings(value: String): ImmutableList<String> {
        val type = object : TypeToken<List<String>>() {  }.type

        return Gson().fromJson<List<String>>(value, type).toImmutableList()
    }

    @Suppress("HasPlatformType")
    @TypeConverter
    fun immutableOptionsToJson(value: ImmutableList<Option>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToImmutableOptions(value: String): ImmutableList<Option> {
        val type = object : TypeToken<ImmutableList<Option>>() {  }.type

        val gson = GsonBuilder().registerTypeAdapter(
            type,
            object : JsonDeserializer<ImmutableList<Option>> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): ImmutableList<Option> {
                    val idioms = json?.asJsonArray?.map { jsonElement ->
                        val jsonObject = jsonElement.asJsonObject
                        val chineseCharacters = jsonObject.get("chineseCharacters").asString
                        val chineseMeanings = jsonObject.get("chineseMeanings").asJsonArray.map { it.asString }
                        val description = jsonObject.get("description").asString
                        val koreanCharacters = jsonObject.get("koreanCharacters").asString

                        object : Option() {
                            override val chineseCharacters: String = chineseCharacters
                            override val chineseMeanings: ImmutableList<String> = chineseMeanings.toImmutableList()
                            override val description: String = description
                            override val koreanCharacters: String = koreanCharacters
                        }
                    } ?: emptyList()

                    return idioms.toImmutableList()
                }
            }
        ).create()

        return gson.fromJson(value, type)
    }
}