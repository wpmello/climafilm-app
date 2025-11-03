package com.example.climafilm.data.repository

import com.example.climafilm.data.source.remote.ApiService
import com.example.climafilm.data.source.remote.model.movie.MovieResponse
import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.data.source.remote.model.movie.detail.MovieDetailResponse
import com.example.climafilm.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    override suspend fun getPlayingNowMovies(): Response<PosterResponse> {
        return apiService.getPlayingNowMovies()
    }

    override suspend fun getPopularMovies(): Response<PosterResponse> {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): Response<PosterResponse> {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getUpcomingMovies(): Response<PosterResponse> {
        return apiService.getUpcomingMovies()
    }

    override suspend fun getMovieDetailsById(movieId: Int): Response<MovieDetailResponse> {
        return apiService.getMovieDetailsById(movieId = movieId)
    }

    override suspend fun getMoviePerCity(city: String): Response<Map<Long, List<MovieResponse>>> {
        return apiService.getMoviePerCity(city = city)
    }
}