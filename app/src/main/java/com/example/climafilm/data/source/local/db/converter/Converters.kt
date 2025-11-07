package com.example.climafilm.data.source.local.db.converter

import androidx.room.TypeConverter
import com.example.climafilm.data.source.remote.model.movie.detail.GenreResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(list: List<Int>?): String = gson.toJson(list)

    @TypeConverter
    fun toIntList(json: String): List<Int> =
        gson.fromJson(json, object : TypeToken<List<Int>>() {}.type)

    @TypeConverter
    fun fromGenreList(genres: List<GenreResponse>?): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(json: String): List<GenreResponse> {
        val type = object : TypeToken<List<GenreResponse>>() {}.type
        return Gson().fromJson(json, type)
    }
}

