package com.example.climafilm.presentation.viewmodels.base

import androidx.lifecycle.ViewModel
import com.example.climafilm.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T> : ViewModel() {
    protected val _movie = MutableStateFlow<Resource<T>>(Resource.Loading())
    var movie: StateFlow<Resource<T>> = _movie

    init {
        this.fetchMovies()
    }

    protected abstract fun fetchMovies()

    protected fun <T> handleResponse(resource: Resource<T>?): Resource<T> {
        return resource?.data?.let {
            Resource.Success(it)
        } ?: Resource.Error(resource?.message ?: "")
    }
}