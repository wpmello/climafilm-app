package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.domain.model.Movie
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetUpcomingMoviesUseCase
import com.example.climafilm.util.Resource
import javax.inject.Inject

class GetUpcomingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetUpcomingMoviesUseCase {
    override suspend fun invoke(): Resource<List<Movie>> {
        return movieRepository.getUpcomingMovies()
    }
}