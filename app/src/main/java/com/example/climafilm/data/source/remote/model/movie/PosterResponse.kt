package com.example.climafilm.data.source.remote.model.movie

data class PosterResponse(
    val results: List<MovieResponse>,
    val total_results: Int
)