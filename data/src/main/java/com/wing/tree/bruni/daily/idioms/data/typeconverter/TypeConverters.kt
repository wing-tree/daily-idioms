package com.wing.tree.bruni.daily.idioms.data.typeconverter

import androidx.room.TypeConverter
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom
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
    fun immutableIdiomsToJson(value: ImmutableList<Idiom>) = Gson().toJson(value)

    @TypeConverter
    fun jsonToImmutableIdioms(value: String): ImmutableList<Idiom> {
        val type = object : TypeToken<ImmutableList<Idiom>>() {  }.type

        val gson = GsonBuilder().registerTypeAdapter(
            type,
            object : JsonDeserializer<ImmutableList<Idiom>> {
                override fun deserialize(
                    json: JsonElement?,
                    typeOfT: Type?,
                    context: JsonDeserializationContext?
                ): ImmutableList<Idiom> {
                    val idioms = json?.asJsonArray?.map { jsonElement ->
                        val jsonObject = jsonElement.asJsonObject
                        val id = jsonObject.get("id").asInt
                        val category = jsonObject.get("category").asInt
                        val chineseCharacters = jsonObject.get("chineseCharacters").asString
                        val chineseMeanings = jsonObject.get("chineseMeanings").asJsonArray.map { it.asString }
                        val description = jsonObject.get("description").asString
                        val koreanCharacters = jsonObject.get("koreanCharacters").asString
                        val my = jsonObject.get("my").asBoolean

                        Idiom(
                            id = id,
                            category = category,
                            chineseCharacters = chineseCharacters,
                            chineseMeanings = chineseMeanings.toImmutableList(),
                            description = description,
                            koreanCharacters = koreanCharacters,
                            my = my
                        )
                    } ?: emptyList()

                    return idioms.toImmutableList()
                }
            }
        ).create()

        return gson.fromJson(value, type)
    }
}