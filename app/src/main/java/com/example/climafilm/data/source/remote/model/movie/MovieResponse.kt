package com.example.climafilm.data.source.remote.model.movie

import com.example.climafilm.domain.model.Movie

data class MovieResponse(
    val adult: Boolean,
    val genre_ids: List<Int>,
    val id: Int,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String,
    val title: String
) {
    fun toEntity(): Movie {
        return Movie(
            adult = adult,
            genre_ids = genre_ids,
            id = id,
            original_title = original_title,
            overview = overview,
            popularity = popularity,
            vote_average = vote_average,
            vote_count = vote_count,
            backdrop_path = backdrop_path,
            poster_path = poster_path,
            release_date = release_date,
            title = title
        )
    }
}