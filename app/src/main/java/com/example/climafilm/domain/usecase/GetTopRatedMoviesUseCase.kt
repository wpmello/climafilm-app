package com.example.climafilm.domain.usecase

import com.example.climafilm.domain.model.Movie
import com.example.climafilm.util.Resource

interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(): Resource<List<Movie>>
}