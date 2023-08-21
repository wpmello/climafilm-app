package com.example.climafilm.ui.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.climafilm.databinding.CarouselMovieItemBinding
import com.example.climafilm.domain.model.Movie

class CarouselMovieAdapter(private var movieList: MutableList<Movie>) :
    RecyclerView.Adapter<CarouselMovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            CarouselMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
         holder.binding.apply {
             Glide.with(movieImage).load(movie.image).into(movieImage)
         }
    }

    override fun getItemCount(): Int = movieList.size

    inner class MovieViewHolder(val binding: CarouselMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}