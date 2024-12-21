package com.example.climafilm.domain.usecase

import com.example.climafilm.data.model.detail.MovieDetail
import retrofit2.Response

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Response<MovieDetail>
}