package com.example.climafilm.ui.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.climafilm.databinding.MovieItemViewPagerBinding
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.ui.adapters.BaseMovieAdapter
import com.example.climafilm.util.CommonComponents
import com.example.climafilm.util.Constants.Companion.BASE_IMAGE_URL
import javax.inject.Inject

class MoviePagerAdapter @Inject constructor(
    private val requestManager: RequestManager
) : RecyclerView.Adapter<MoviePagerAdapter.ViewHolder>() {

    private var movieList: List<Movie> = listOf()
    private var listener: BaseMovieAdapter.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemViewPagerBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size / 2

    fun setList(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: BaseMovieAdapter.OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(private val binding: MovieItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            val genreName = movie.genre_ids?.let { mapGenreIdsToNames(it).first() }

            binding.imageMovieName.text = movie.title
            binding.releaseDate.text = movie.release_date?.let {
                CommonComponents.getFormattedDate(
                    it
                )
            }
            binding.genre.text = genreName
            binding.voteAverage.text = movie.vote_average.toString()
            binding.voteCount.text = movie.vote_count.toString()
            requestManager.load(BASE_IMAGE_URL + movie.backdrop_path)
                .into(binding.backGroundImageMovie)
            requestManager.load(BASE_IMAGE_URL + movie.poster_path).into(binding.imageMovie)
            binding.layout.setOnClickListener { listener?.onItemClick(movie.id) }
        }
    }
}

