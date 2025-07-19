package com.example.climafilm.domain.usecase

import com.example.climafilm.data.model.MovieResponse
import retrofit2.Response

interface GetMoviesPerCityUseCase {
    suspend operator fun invoke(city: String): Response<Map<Long, List<MovieResponse>>>
}