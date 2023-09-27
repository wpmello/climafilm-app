package com.example.climafilm.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climafilm.data.model.Poster
import com.example.climafilm.util.Resource
import retrofit2.Response

abstract class BaseViewModel() : ViewModel() {
    protected val _movieList = MutableLiveData<Resource<Poster>>()
    var movieList: LiveData<Resource<Poster>> = _movieList

    init {
        fetchMovies()
    }

    protected abstract fun fetchMovies()

    protected fun handleMovieResponse(response: Response<Poster>?): Resource<Poster>? {
        response?.let {
            if (it.isSuccessful) {
                it.body()?.let { resultResponse ->
                    return Resource.Success(resultResponse)
                }
            }
        }
        return response?.message()?.let { Resource.Error(it) }
    }
}