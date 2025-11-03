package com.example.climafilm.data.source.remote.model.movie.detail

data class MovieDetailResponse(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Long,
    val genreResponses: List<GenreResponse>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompanyResponse>,
    val production_countries: List<ProductionCountryResponse>,
    val release_date: String?,
    val revenue: Long,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguageResponse>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)