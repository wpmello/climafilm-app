package com.example.climafilm.domain.usecase

import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.repository.MovieRepository
import retrofit2.Response
import javax.inject.Inject

class GetPlayingNowMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetPlayingNowMoviesUseCase {
    override suspend fun invoke(): Response<Poster> {
        return movieRepository.getPlayingNowMovie()
    }
}