package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.domain.model.Movie
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetMoviesPerCityUseCase
import com.example.climafilm.util.Resource
import javax.inject.Inject

class GetMoviesPerCityUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMoviesPerCityUseCase {
    override suspend fun invoke(city: String): Resource<Map<Long, List<Movie>>> {
        return movieRepository.getMoviePerCity(city)
    }
}