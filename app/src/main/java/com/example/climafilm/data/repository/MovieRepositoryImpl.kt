package com.example.climafilm.data.repository

import com.example.climafilm.data.source.local.db.dao.MovieDao
import com.example.climafilm.data.source.local.db.dao.MovieDetailDao
import com.example.climafilm.data.source.local.db.entity.MovieEntity
import com.example.climafilm.data.source.local.db.entity.toDomain
import com.example.climafilm.data.source.local.db.entity.toMovieDetailFallback
import com.example.climafilm.data.source.remote.ApiService
import com.example.climafilm.data.source.remote.model.movie.PosterResponse
import com.example.climafilm.data.source.remote.model.movie.detail.toEntity
import com.example.climafilm.data.source.remote.model.movie.toDomain
import com.example.climafilm.data.source.remote.model.movie.toEntity
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.domain.model.MovieDetail
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.util.NetworkChecker
import com.example.climafilm.util.Resource
import retrofit2.Response
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao,
    private val movieDetailDao: MovieDetailDao,
    private val networkChecker: NetworkChecker
) : MovieRepository {

    companion object {
        private const val ONE_DAY_CACHE_DURATION = 24 * 60 * 60 * 1000L
        private const val NOW_PLAYING = "now_playing"
        private const val POPULAR = "popular"
        private const val TOP_RATED = "top_rated"
        private const val UPCOMING = "upcoming"

    }

    override suspend fun getPlayingNowMovies() = getMoviesByCategory(NOW_PLAYING)
    override suspend fun getPopularMovies() = getMoviesByCategory(POPULAR)
    override suspend fun getTopRatedMovies() = getMoviesByCategory(TOP_RATED)
    override suspend fun getUpcomingMovies() = getMoviesByCategory(UPCOMING)

    private suspend fun getMoviesByCategory(category: String): Resource<List<Movie>> {
        return getMoviesWithCache(
            category = category,
            apiCall = { fetchMoviesFromApi(category) }
        )
    }

    private suspend fun fetchMoviesFromApi(category: String): Response<PosterResponse> {
        return when (category) {
            NOW_PLAYING -> apiService.getPlayingNowMovies()
            POPULAR -> apiService.getPopularMovies()
            TOP_RATED -> apiService.getTopRatedMovies()
            else -> apiService.getUpcomingMovies()
        }
    }

    override suspend fun getMovieDetailsById(id: Int): Resource<MovieDetail> {
        val cached = movieDetailDao.getMovieDetailById(id)
        val isCacheValid = cached != null && !isCacheExpired(cached.lastUpdated)

        return when {
            networkChecker.hasInternetConnection() -> fetchMovieDetailsFromApi(id)
            cached != null && isCacheValid -> Resource.Success(cached.toDomain())
            else -> getDetailFromMovieFallback(id)
        }
    }

    private suspend fun fetchMovieDetailsFromApi(id: Int): Resource<MovieDetail> {
        return try {
            val response = apiService.getMovieDetailsById(id)
            if (!response.isSuccessful) return Resource.Error("Erro: ${response.message()}")

            val entity = response.body()?.toEntity() ?: return Resource.Error("Corpo da resposta nulo")

            movieDetailDao.deleteMovieDetailById(entity.id)
            movieDetailDao.insertMovieDetail(entity)

            Resource.Success(entity.toDomain())
        } catch (e: Exception) {
            getDetailFromMovieFallback(id)
        }
    }

    override suspend fun getMoviePerCity(city: String): Resource<Map<Long, List<Movie>>> {
        return try {
            val response = apiService.getMoviePerCity(city)

            val moviesPerTemp = response.body()?.mapValues { entry ->
                entry.value.map { it.toDomain() }
            } ?: return Resource.Error("Erro ao obter filmes da cidade $city")

            if (moviesPerTemp.values.all {it.isEmpty() }) {
                return Resource.Error("A temperatura atual na ${city} é ${moviesPerTemp.keys.first()}°C, mas não possui nenhum filme com o genêro correspondente a esta temperatura no momento.")
            }

            Resource.Success(moviesPerTemp)
        } catch (e: Exception) {
            Resource.Error("Serviço indisponível ou sem internet \uD83D\uDEDC")
        }
    }

    private suspend fun getMoviesWithCache(
        category: String,
        apiCall: suspend () -> Response<PosterResponse>
    ): Resource<List<Movie>> {
        val cachedMovies = movieDao.getMoviesByCategory(category)
        val isCacheExpired = cachedMovies.firstOrNull()?.let { isCacheExpired(it.lastUpdated) } ?: true

        return if (networkChecker.hasInternetConnection() && isCacheExpired) {
            updateCacheFromApi(category, apiCall, cachedMovies)
        } else if (cachedMovies.isNotEmpty()) {
            Resource.Success(cachedMovies.map { it.toDomain() })
        } else {
            Resource.Error("Sem cache disponível e falha na atualização.")
        }
    }

    private suspend fun updateCacheFromApi(
        category: String,
        apiCall: suspend () -> Response<PosterResponse>,
        cachedMovies: List<MovieEntity>
    ): Resource<List<Movie>> {
        return try {
            val response = apiCall()
            if (!response.isSuccessful) return fallbackOrError(response.message(), cachedMovies)

            val body = response.body() ?: return fallbackOrError("Resposta nula", cachedMovies)
            val entities = body.results.map { it.toEntity(category) }

            movieDao.clearMoviesByCategory(category)
            movieDao.insertMovies(entities)

            val updatedCache = movieDao.getMoviesByCategory(category)
            Resource.Success(updatedCache.map { it.toDomain() })
        } catch (e: Exception) {
            fallbackOrError("Erro ao buscar filmes: ${e.message}", cachedMovies)
        }
    }

    private fun fallbackOrError(message: String, cachedMovies: List<MovieEntity>): Resource<List<Movie>> {
        return if (cachedMovies.isNotEmpty()) {
            Resource.Success(cachedMovies.map { it.toDomain() })
        } else {
            Resource.Error(message)
        }
    }

    private fun isCacheExpired(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > ONE_DAY_CACHE_DURATION
    }

    private suspend fun getDetailFromMovieFallback(id: Int): Resource<MovieDetail> {
        return movieDao.getMovieById(id)?.let {
            Resource.Success(it.toMovieDetailFallback())
        } ?: Resource.Error("Sem cache nem dados locais disponíveis.")
    }
}