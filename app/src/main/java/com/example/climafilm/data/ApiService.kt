package com.example.climafilm.data

import com.example.climafilm.data.model.Poster
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("on-playing")
    suspend fun getPlayingNowMovies(): Response<Poster>

    @GET("popular-movies")
    suspend fun getPopularMovies(): Response<Poster>

    @GET("top-rated-movies")
    suspend fun getTopRatedMovies(): Response<Poster>

    @GET("upcoming-movies")
    suspend fun getUpcomingMovies(): Response<Poster>
}