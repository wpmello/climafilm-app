package com.example.climafilm.ui.movies

import com.example.climafilm.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movieList: List<Movie> = emptyList(),
    val error: String = ""
)