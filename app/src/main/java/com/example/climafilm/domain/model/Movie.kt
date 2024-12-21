package com.example.climafilm.domain.model

data class Movie(
    val adult: Boolean,
    val id: Int,
    val genre_ids: List<Int>?,
    val overview: String?,
    val original_title: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val backdrop_path: String?,
    val title: String?
)