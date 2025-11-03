package com.example.climafilm.data.source.remote

import com.example.climafilm.data.source.remote.model.movie.MovieResponse
import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.data.source.remote.model.movie.detail.MovieDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/on-playing")
    suspend fun getPlayingNowMovies(): Response<PosterResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<PosterResponse>

    @GET("movie/top-rated")
    suspend fun getTopRatedMovies(): Response<PosterResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<PosterResponse>

    @GET("movie/movie-detail/{id}")
    suspend fun getMovieDetailsById(@Path("id") movieId: Int): Response<MovieDetailResponse>

    @GET("movie/on-playing/{city}")
    suspend fun getMoviePerCity(@Path("city") city: String): Response<Map<Long, List<MovieResponse>>>
}