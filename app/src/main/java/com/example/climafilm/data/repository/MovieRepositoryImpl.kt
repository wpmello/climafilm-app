package com.example.climafilm.data.repository

import com.example.climafilm.data.source.remote.ApiService
import com.example.climafilm.data.model.MovieResponse
import com.example.climafilm.data.model.Poster
import com.example.climafilm.data.model.detail.MovieDetail
import com.example.climafilm.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    override suspend fun getPlayingNowMovies(): Response<Poster> {
        return apiService.getPlayingNowMovies()
    }

    override suspend fun getPopularMovies(): Response<Poster> {
        return apiService.getPopularMovies()
    }

    override suspend fun getTopRatedMovies(): Response<Poster> {
        return apiService.getTopRatedMovies()
    }

    override suspend fun getUpcomingMovies(): Response<Poster> {
        return apiService.getUpcomingMovies()
    }

    override suspend fun getMovieDetailsById(movieId: Int): Response<MovieDetail> {
        return apiService.getMovieDetailsById(movieId = movieId)
    }

    override suspend fun getMoviePerCity(city: String): Response<Map<Long, List<MovieResponse>>> {
        return apiService.getMoviePerCity(city = city)
    }
}