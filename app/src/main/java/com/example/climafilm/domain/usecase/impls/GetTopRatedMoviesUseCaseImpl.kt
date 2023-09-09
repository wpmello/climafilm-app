package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetTopRatedMoviesUseCase
import retrofit2.Response
import javax.inject.Inject

class GetTopRatedMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetTopRatedMoviesUseCase {
    override suspend fun invoke(): Response<Poster> {
        return movieRepository.getTopRatedMovies()
    }
}