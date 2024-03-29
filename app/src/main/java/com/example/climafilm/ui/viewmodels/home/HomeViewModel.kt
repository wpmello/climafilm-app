package com.example.climafilm.ui.viewmodels.home

import androidx.lifecycle.viewModelScope
import com.example.climafilm.domain.usecase.GetPlayingNowMoviesUseCase
import com.example.climafilm.ui.viewmodels.BaseViewModel
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playingNowMoviesUseCase: GetPlayingNowMoviesUseCase
) : BaseViewModel() {
    override fun fetchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.postValue(Resource.Loading())
            val response = playingNowMoviesUseCase.invoke()
            _movieList.postValue(handleMovieResponse(response))
        }
    }
}