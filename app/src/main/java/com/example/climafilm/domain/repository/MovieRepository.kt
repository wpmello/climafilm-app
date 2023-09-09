package com.example.climafilm.domain.repository

import com.example.climafilm.data.model.Poster
import retrofit2.Response

interface MovieRepository {
    suspend fun getPlayingNowMovies(): Response<Poster>
    suspend fun getPopularMovies(): Response<Poster>
    suspend fun getTopRatedMovies(): Response<Poster>
    suspend fun getUpcomingMovies(): Response<Poster>
}