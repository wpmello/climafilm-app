package com.example.climafilm.domain.usecase

import com.example.climafilm.data.source.remote.model.movie.detail.MovieDetailResponse
import retrofit2.Response

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Response<MovieDetailResponse>
}