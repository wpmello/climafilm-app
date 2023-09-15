package com.example.climafilm.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.climafilm.domain.usecase.GetTopRatedMoviesUseCase
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : BaseViewModel() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.postValue(Resource.Loading())
            val response = topRatedMoviesUseCase.invoke()
            _movieList.postValue(handleMovieResponse(response))
        }
    }
}