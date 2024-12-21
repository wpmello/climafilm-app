package com.example.climafilm.ui.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.RequestManager
import com.example.climafilm.databinding.MovieItemRicyclerViewBinding
import com.example.climafilm.ui.adapters.BaseMovieAdapter
import javax.inject.Inject

class TopRatedMoviesAdapter @Inject constructor(
    requestManager: RequestManager
) : BaseMovieAdapter(requestManager) {
    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return MovieItemRicyclerViewBinding.inflate(inflater, parent, false)
    }
}