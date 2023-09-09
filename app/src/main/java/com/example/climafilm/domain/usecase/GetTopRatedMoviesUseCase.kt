package com.example.climafilm.domain.usecase

import com.example.climafilm.data.model.Poster
import retrofit2.Response

interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(): Response<Poster>
}