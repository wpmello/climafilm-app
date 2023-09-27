package com.example.climafilm.ui.viewmodels.home

import androidx.lifecycle.viewModelScope
import com.example.climafilm.domain.usecase.GetUpcomingMoviesUseCase
import com.example.climafilm.ui.viewmodels.BaseViewModel
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val upcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : BaseViewModel() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.postValue(Resource.Loading())
            val response = upcomingMoviesUseCase.invoke()
            _movieList.postValue(handleMovieResponse(response))
        }
    }
}