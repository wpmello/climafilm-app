package com.example.climafilm.data.source.remote

import com.example.climafilm.data.model.MovieResponse
import com.example.climafilm.data.model.Poster
import com.example.climafilm.data.model.detail.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("app/movie/on-playing")
    suspend fun getPlayingNowMovies(): Response<Poster>

    @GET("app/movie/popular")
    suspend fun getPopularMovies(): Response<Poster>

    @GET("app/movie/top-rated")
    suspend fun getTopRatedMovies(): Response<Poster>

    @GET("app/movie/upcoming")
    suspend fun getUpcomingMovies(): Response<Poster>

    @GET("app/movie/movie-detail/{id}")
    suspend fun getMovieDetailsById(@Path("id") movieId: Int): Response<MovieDetail>

    @GET("app/movie/on-playing/{city}")
    suspend fun getMoviePerCity(@Path("city") city: String): Response<Map<Long, List<MovieResponse>>>
}