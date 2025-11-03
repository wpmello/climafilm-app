package com.example.climafilm.presentation.viewmodels.home

import androidx.lifecycle.viewModelScope
import com.example.climafilm.data.source.remote.model.movie.PosterResponse
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
) : BaseViewModel<PosterResponse>() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.value = Resource.Loading()
            val response = popularMoviesUseCase.invoke()
            _movie.value = handleResponse(response)
        }
    }
}