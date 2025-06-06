package com.example.climafilm.presentation.viewmodels.home

import androidx.lifecycle.viewModelScope
import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.usecase.GetTopRatedMoviesUseCase
import com.example.climafilm.presentation.viewmodels.base.BaseViewModel
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : BaseViewModel<Poster>() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.value = Resource.Loading()
            val response = topRatedMoviesUseCase.invoke()
            _movie.value = handleResponse(response) ?: Resource.Error("Error")
        }
    }
}