package com.example.climafilm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.databinding.MovieItemViewPagerBinding
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.util.Constants.Companion.BASE_IMAGE_URL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MoviePagerAdapter @Inject constructor(
    private val requestManager: RequestManager
) : RecyclerView.Adapter<MoviePagerAdapter.ViewHolder>() {

    private lateinit var movieList: List<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemViewPagerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size / 2
    }

    fun setList(list: List<Movie>) {
        movieList = list
        notifyDataSetChanged()
    }

    private fun getDate(movie: Movie): String {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val desiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = originalFormat.parse(movie.release_date)
            date?.let { desiredFormat.format(it) }.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            "Something went wrong"
        }
    }

    inner class ViewHolder(private val binding: MovieItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {

            val genreName = mapGenreIdsToNames(movie.genre_ids).first()

            binding.imageMovieName.text = movie.title
            binding.releaseDate.text = getDate(movie)
            binding.genre.text = genreName
            binding.voteAverage.text = movie.vote_average.toString()
            binding.voteCount.text = movie.vote_count.toString()
            requestManager.load(BASE_IMAGE_URL + movie.backdrop_path)
                .into(binding.backGroundImageMovie)
            requestManager.load(BASE_IMAGE_URL + movie.poster_path).into(binding.imageMovie)
        }
    }
}

