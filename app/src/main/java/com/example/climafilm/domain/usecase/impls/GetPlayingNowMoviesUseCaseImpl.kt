package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetPlayingNowMoviesUseCase
import retrofit2.Response
import javax.inject.Inject

class GetPlayingNowMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetPlayingNowMoviesUseCase {
    override suspend fun invoke(): Response<PosterResponse> {
        return movieRepository.getPlayingNowMovies()
    }
}