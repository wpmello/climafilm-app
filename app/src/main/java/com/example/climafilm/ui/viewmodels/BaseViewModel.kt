package com.example.climafilm.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.climafilm.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response

abstract class BaseViewModel<T> : ViewModel() {
    protected val _movie = MutableStateFlow<Resource<T>>(Resource.Loading())
    var movie: StateFlow<Resource<T>> = _movie

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