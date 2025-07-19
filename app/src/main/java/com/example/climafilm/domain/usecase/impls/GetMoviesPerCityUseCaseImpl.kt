package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.model.MovieResponse
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetMoviesPerCityUseCase
import retrofit2.Response
import javax.inject.Inject

class GetMoviesPerCityUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMoviesPerCityUseCase {
    override suspend fun invoke(city: String): Response<Map<Long, List<MovieResponse>>> {
        return movieRepository.getMoviePerCity(city)
    }
}