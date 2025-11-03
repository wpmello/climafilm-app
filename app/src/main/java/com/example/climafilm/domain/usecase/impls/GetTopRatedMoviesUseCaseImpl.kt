package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetTopRatedMoviesUseCase
import retrofit2.Response
import javax.inject.Inject

class GetTopRatedMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetTopRatedMoviesUseCase {
    override suspend fun invoke(): Response<PosterResponse> {
        return movieRepository.getTopRatedMovies()
    }
}