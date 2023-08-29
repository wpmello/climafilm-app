package com.example.climafilm.data.model

import com.example.climafilm.domain.model.Movie

data class MovieResponse(
    val adult: Boolean,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val overview: String,
    val popularity: Int,
    val poster_path: String,
    val release_date: String,
    val title: String
) {
    fun toEntity(): Movie {
        return Movie(
            id = id,
            genre_ids  = genre_ids,
            overview = overview,
            popularity = popularity,
            poster_path = poster_path,
            title = title
        )
    }
}