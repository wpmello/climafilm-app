package com.example.climafilm.data.source.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.domain.model.MovieDetail

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean,
    val genre_ids: List<Int>,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val vote_average: Double,
    val vote_count: Int,
    val backdrop_path: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val category: String,
    val lastUpdated: Long = System.currentTimeMillis()
)

fun MovieEntity.toDomain() = Movie(
    id = id,
    adult = adult,
    genre_ids = genre_ids,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    vote_average = vote_average,
    vote_count = vote_count,
    backdrop_path = backdrop_path,
    poster_path = poster_path,
    release_date = release_date,
    title = title
)

fun MovieEntity.toMovieDetailFallback() = MovieDetail(
    id = id,
    backdrop_path = backdrop_path,
    budget = null,
    genres = emptyList(),
    overview = overview,
    poster_path = poster_path,
    release_date = release_date,
    revenue = null,
    runtime = null,
    tagline = null,
    title = title,
    vote_average = vote_average,
    vote_count = vote_count
)