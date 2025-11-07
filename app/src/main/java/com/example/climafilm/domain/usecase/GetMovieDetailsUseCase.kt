package com.example.climafilm.domain.usecase

import com.example.climafilm.domain.model.MovieDetail
import com.example.climafilm.util.Resource

interface GetMovieDetailsUseCase {
    suspend operator fun invoke(movieId: Int): Resource<MovieDetail>
}