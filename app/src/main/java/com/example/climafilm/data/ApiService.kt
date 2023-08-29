package com.example.climafilm.data

import com.example.climafilm.data.model.Poster
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("on-playing")
    suspend fun getPlayingNowMovies(): Response<Poster>
}