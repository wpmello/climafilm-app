package com.example.climafilm.di

import com.example.climafilm.data.ApiService
import com.example.climafilm.data.repository.MovieRepositoryImpl
import com.example.climafilm.domain.repository.MovieRepository
import com.example.climafilm.domain.usecase.GetPlayingNowMoviesUseCase
import com.example.climafilm.domain.usecase.GetPlayingNowMoviesUseCaseImpl
import com.example.climafilm.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
            .baseUrl(Constants.BASE_URL)
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
    fun providesGetPlayingNowMoviesUseCase(movieRepository: MovieRepository): GetPlayingNowMoviesUseCase {
        return GetPlayingNowMoviesUseCaseImpl(movieRepository)
    }

    @Provides
    @Singleton
    fun providesMovieRepository(apiService: ApiService): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }
}