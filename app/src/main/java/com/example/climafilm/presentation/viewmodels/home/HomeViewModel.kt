package com.example.climafilm.presentation.viewmodels.home

import androidx.lifecycle.viewModelScope
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.domain.usecase.GetPlayingNowMoviesUseCase
import com.example.climafilm.presentation.viewmodels.base.BaseViewModel
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playingNowMoviesUseCase: GetPlayingNowMoviesUseCase
) : BaseViewModel<List<Movie>>() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.value = Resource.Loading()
            val resource = playingNowMoviesUseCase.invoke()
            _movie.value = handleResponse(resource)
        }
    }
}