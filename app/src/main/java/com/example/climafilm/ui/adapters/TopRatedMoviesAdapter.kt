package com.example.climafilm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.climafilm.databinding.MovieItemRicyclerViewBinding
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.util.Constants.Companion.BASE_IMAGE_URL
import javax.inject.Inject

class TopRatedMoviesAdapter @Inject constructor(
    private val requestManager: RequestManager
) : RecyclerView.Adapter<TopRatedMoviesAdapter.ViewHolder>() {

    private lateinit var movieList: List<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemRicyclerViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movieList.size

    fun setList(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: MovieItemRicyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            requestManager.load(BASE_IMAGE_URL + movie.poster_path).into(binding.imageMovie)
            binding.txtMovieName.text = movie.title
        }
    }
}