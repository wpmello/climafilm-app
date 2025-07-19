package com.example.climafilm.presentation.viewmodels.base

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

    protected fun <T> handleResponse(response: Response<T>?): Resource<T> {
        //Todo: create string resource
        if (response == null) {
            return Resource.Error("Resposta nula da API.")
        }

        if (response.isSuccessful) {
            val body = response.body()
            return if (body != null) {
                Resource.Success(body)
            } else {
                Resource.Error("Corpo da resposta vazio, mas esperado.")
            }
        }

        val errorBody = response.errorBody()?.string()
        val errorMessage = response.message()

        val detailedErrorMessage = buildString {
            append("Erro na API: ${response.code()}")
            if (!errorMessage.isNullOrBlank()) {
                append(" - $errorMessage")
            }
            if (!errorBody.isNullOrBlank()) {
                append(" (Detalhes: $errorBody)")
            }
            if (isBlank()) {
                append("Erro desconhecido na API.")
            }
        }
        return Resource.Error(detailedErrorMessage)
    }
}