package com.example.climafilm.ui.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.RequestManager
import com.example.climafilm.databinding.MovieItemRicyclerViewBinding
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.ui.adapters.BaseMovieAdapter
import com.example.climafilm.util.Constants.Companion.BASE_IMAGE_URL
import javax.inject.Inject

class UpcomingMoviesAdapter @Inject constructor(
    private val requestManager: RequestManager
) : BaseMovieAdapter<Movie>() {
    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return MovieItemRicyclerViewBinding.inflate(inflater, parent, false)
    }

    override fun bind(holder: ViewHolder, movie: Movie) {
        val binding = holder.binding as MovieItemRicyclerViewBinding
        requestManager.load(BASE_IMAGE_URL + movie.poster_path).into(binding.imageMovie)
        binding.txtMovieName.text = movie.title
    }
}