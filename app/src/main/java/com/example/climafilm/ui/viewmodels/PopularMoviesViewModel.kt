package com.example.climafilm.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.climafilm.domain.usecase.GetPopularMoviesUseCase
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val popularMoviesUseCase: GetPopularMoviesUseCase
) : BaseViewModel() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.postValue(Resource.Loading())
            val response = popularMoviesUseCase.invoke()
            _movieList.postValue(handleMovieResponse(response))
        }
    }
}