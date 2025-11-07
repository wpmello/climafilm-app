package com.example.climafilm.data.source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.climafilm.data.source.remote.model.movie.detail.GenreResponse
import com.example.climafilm.domain.model.MovieDetail

@Entity(tableName = "movie_detail")
data class MovieDetailEntity(
    @PrimaryKey
    val id: Int,
    val backdrop_path: String,
    val budget: Long,
    val genres: List<GenreResponse>,
    val overview: String,
    val poster_path: String,
    val release_date: String?,
    val revenue: Long,
    val runtime: Int,
    val tagline: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val lastUpdated: Long = System.currentTimeMillis()
)

fun MovieDetailEntity.toDomain(): MovieDetail {
    return MovieDetail(
        id = id,
        backdrop_path = backdrop_path,
        budget = budget,
        genres = genres,
        overview = overview,
        poster_path = poster_path,
        release_date = release_date,
        revenue = revenue,
        runtime = runtime,
        tagline = tagline,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count
    )
}