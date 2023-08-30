package com.example.climafilm.domain.repository

import com.example.climafilm.data.model.Poster
import retrofit2.Response

interface MovieRepository {
    suspend fun getPlayingNowMovie(): Response<Poster>
}