package com.example.climafilm.domain.repository

import com.example.climafilm.domain.model.Movie
import com.example.climafilm.domain.model.MovieDetail
import com.example.climafilm.util.Resource

interface MovieRepository {
    suspend fun getPlayingNowMovies(): Resource<List<Movie>>
    suspend fun getPopularMovies(): Resource<List<Movie>>
    suspend fun getTopRatedMovies(): Resource<List<Movie>>
    suspend fun getUpcomingMovies(): Resource<List<Movie>>
    suspend fun getMovieDetailsById(id: Int): Resource<MovieDetail>
    suspend fun getMoviePerCity(city: String): Resource<Map<Long, List<Movie>>>
}