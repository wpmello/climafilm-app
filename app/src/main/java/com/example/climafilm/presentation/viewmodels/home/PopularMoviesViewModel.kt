package com.example.climafilm.presentation.viewmodels.home

import androidx.lifecycle.viewModelScope
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.domain.usecase.GetPopularMoviesUseCase
import com.example.climafilm.presentation.viewmodels.base.BaseViewModel
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val popularMoviesUseCase: GetPopularMoviesUseCase
) : BaseViewModel<List<Movie>>() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.value = Resource.Loading()
            val resource = popularMoviesUseCase.invoke()
            _movie.value = handleResponse(resource)
        }
    }
}