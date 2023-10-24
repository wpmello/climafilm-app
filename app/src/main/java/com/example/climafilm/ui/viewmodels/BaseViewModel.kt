package com.example.climafilm.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climafilm.util.Resource
import retrofit2.Response

abstract class BaseViewModel<T> : ViewModel() {
    protected val _movie = MutableLiveData<Resource<T>>()
    var movie: LiveData<Resource<T>> = _movie

    init {
        this.fetchMovies()
    }

    protected abstract fun fetchMovies()

    protected fun handleResponse(response: Response<T>?): Resource<T>? {
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