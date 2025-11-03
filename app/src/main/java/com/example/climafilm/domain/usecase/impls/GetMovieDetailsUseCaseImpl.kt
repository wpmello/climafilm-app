package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.source.remote.model.movie.detail.MovieDetailResponse
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetMovieDetailsUseCase
import retrofit2.Response
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {
    override suspend fun invoke(movieId: Int): Response<MovieDetailResponse> {
        return movieRepository.getMovieDetailsById(movieId)
    }
}