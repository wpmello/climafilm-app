package com.example.climafilm.di

import android.content.Context
import androidx.room.Room
import com.example.climafilm.data.source.remote.ApiService
import com.example.climafilm.data.repository.MovieRepositoryImpl
import com.example.climafilm.data.source.local.AppPreferences
import com.example.climafilm.data.source.local.db.AppDatabase
import com.example.climafilm.data.source.local.db.dao.MovieDao
import com.example.climafilm.data.source.local.db.dao.MovieDetailDao
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.*
import com.example.climafilm.domain.usecase.impls.*
import com.example.climafilm.util.Constants.Companion.BASE_URL
import com.example.climafilm.util.NetworkChecker
import com.example.climafilm.util.NetworkCheckerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Provides
    @Singleton
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Provides
    @Singleton
    fun provideMovieDetailDao(db: AppDatabase) = db.movieDetailDao()

    @Provides
    @Singleton
    fun providesMovieRepository(
        apiService: ApiService,
        movieDao: MovieDao,
        movieDetailDao: MovieDetailDao,
        networkChecker: NetworkChecker
        ): MovieRepository {
        return MovieRepositoryImpl(apiService, movieDao, movieDetailDao, networkChecker)
    }

    @Provides
    @Singleton
    fun providesGetPlayingNowMoviesUseCase(movieRepository: MovieRepository): GetPlayingNowMoviesUseCase {
        return GetPlayingNowMoviesUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun providesGetPopularMoviesUseCase(movieRepository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun providesGetTopRatedMoviesUseCase(movieRepository: MovieRepository): GetTopRatedMoviesUseCase {
        return GetTopRatedMoviesUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun providesGetUpcomingMoviesUseCase(movieRepository: MovieRepository): GetUpcomingMoviesUseCase {
        return GetUpcomingMoviesUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun providesGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun providesGetMoviesPerCityUseCase(movieRepository: MovieRepository): GetMoviesPerCityUseCase {
        return GetMoviesPerCityUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun providesAppPreferences(@ApplicationContext context: Context): AppPreferences {
        return AppPreferences(context)
    }

    @Provides
    @Singleton
    fun providesNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkCheckerImpl(context)
    }
}