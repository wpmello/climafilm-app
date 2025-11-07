package com.example.climafilm.domain.usecase

import com.example.climafilm.domain.model.Movie
import com.example.climafilm.util.Resource

interface GetMoviesPerCityUseCase {
    suspend operator fun invoke(city: String): Resource<Map<Long, List<Movie>>>
}