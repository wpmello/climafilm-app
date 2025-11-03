package com.example.climafilm.domain.repository

import com.example.climafilm.data.source.remote.model.movie.MovieResponse
import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.data.source.remote.model.movie.detail.MovieDetailResponse
import retrofit2.Response

interface MovieRepository {
    suspend fun getPlayingNowMovies(): Response<PosterResponse>
    suspend fun getPopularMovies(): Response<PosterResponse>
    suspend fun getTopRatedMovies(): Response<PosterResponse>
    suspend fun getUpcomingMovies(): Response<PosterResponse>
    suspend fun getMovieDetailsById(movieId: Int): Response<MovieDetailResponse>
    suspend fun getMoviePerCity(city: String): Response<Map<Long, List<MovieResponse>>>
}