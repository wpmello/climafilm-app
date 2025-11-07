package com.example.climafilm.domain.usecase.impls

import com.example.climafilm.domain.model.MovieDetail
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetMovieDetailsUseCase
import com.example.climafilm.util.Resource
import javax.inject.Inject

class GetMovieDetailsUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieDetailsUseCase {
    override suspend fun invoke(movieId: Int): Resource<MovieDetail> {
        return movieRepository.getMovieDetailsById(movieId)
    }
}