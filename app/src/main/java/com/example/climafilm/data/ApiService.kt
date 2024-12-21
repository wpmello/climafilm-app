package com.example.climafilm.data

import com.example.climafilm.data.model.Poster
import com.example.climafilm.data.model.detail.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/on-playing")
    suspend fun getPlayingNowMovies(): Response<Poster>

    @GET("movie/popular-movies")
    suspend fun getPopularMovies(): Response<Poster>

    @GET("movie/top-rated-movies")
    suspend fun getTopRatedMovies(): Response<Poster>

    @GET("movie/upcoming-movies")
    suspend fun getUpcomingMovies(): Response<Poster>

    @GET("movie/{id}")
    suspend fun getMovieDetailsById(@Path("id") movieId: Int): Response<MovieDetail>
}