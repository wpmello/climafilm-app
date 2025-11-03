package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetPopularMoviesUseCase
import retrofit2.Response
import javax.inject.Inject

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetPopularMoviesUseCase {
        override suspend fun invoke(): Response<PosterResponse> {
        return movieRepository.getPopularMovies()
    }
}