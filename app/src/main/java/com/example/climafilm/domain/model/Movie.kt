package com.example.climafilm.domain.model

data class Movie(
    val id: Int,
    val genre_ids: List<Int>,
    val overview: String,
    val popularity: Int,
    val poster_path: String,
    val title: String
)