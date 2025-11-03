package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetUpcomingMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class GetUpcomingMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetUpcomingMoviesUseCase {
    override suspend fun invoke(): Response<PosterResponse> {
        return try {
            withContext(Dispatchers.IO) {
                movieRepository.getUpcomingMovies()
            }
        } catch (e: Exception) {
            return Response.error(500, null)
        }
    }
}