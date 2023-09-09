package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetUpcomingMoviesUseCase
import retrofit2.Response
import javax.inject.Inject

class GetUpcomingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetUpcomingMoviesUseCase {
    override suspend fun invoke(): Response<Poster> {
        return movieRepository.getUpcomingMovies()
    }
}