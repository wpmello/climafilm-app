package com.example.climafilm.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.climafilm.data.model.detail.MovieDetail
import com.example.climafilm.domain.usecase.GetMovieDetailsUseCase
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : BaseViewModel<MovieDetail>() {
    override fun fetchMovies() {}

    fun initialize(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.postValue(Resource.Loading())
            val movieResponse = getMovieDetailsUseCase.invoke(movieId)
            _movie.postValue(handleResponse(movieResponse))
        }
    }
}