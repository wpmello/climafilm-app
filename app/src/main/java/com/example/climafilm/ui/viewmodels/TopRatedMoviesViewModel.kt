package com.example.climafilm.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.usecase.GetTopRatedMoviesUseCase
import com.example.climafilm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : ViewModel() {
    private val _movieList = MutableLiveData<Resource<Poster>>()
    var movieList: LiveData<Resource<Poster>> = _movieList

    init {
        getPopularMovies()
    }

    fun getPopularMovies() = viewModelScope.launch {
        _movieList.postValue(Resource.Loading())
        val response = topRatedMoviesUseCase.invoke()
        _movieList.postValue(handleMovieResponse(response))
    }


    private fun handleMovieResponse(response: Response<Poster>): Resource<Poster> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}