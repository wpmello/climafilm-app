package com.example.climafilm.ui.viewmodels.home

import androidx.lifecycle.viewModelScope
import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.usecase.GetTopRatedMoviesUseCase
import com.example.climafilm.ui.viewmodels.BaseViewModel
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
            _movie.postValue(Resource.Loading())
            val response = topRatedMoviesUseCase.invoke()
            _movie.postValue(handleResponse(response))
        }
    }
}