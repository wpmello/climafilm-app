package com.example.climafilm.domain.usecase

import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import retrofit2.Response

interface GetUpcomingMoviesUseCase {
    suspend operator fun invoke(): Response<PosterResponse>
}