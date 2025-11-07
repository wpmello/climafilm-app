package com.example.climafilm.domain.model

import com.example.climafilm.data.source.remote.model.movie.detail.GenreResponse

data class MovieDetail(
    val id: Int,
    val backdrop_path: String,
    val budget: Long?,
    val genres: List<GenreResponse>?,
    val overview: String,
    val poster_path: String,
    val release_date: String?,
    val revenue: Long?,
    val runtime: Int?,
    val tagline: String?,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)