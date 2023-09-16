package com.example.climafilm.ui.adapters.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.RequestManager
import com.example.climafilm.databinding.MovieItemViewPagerBinding
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.ui.adapters.BaseMovieAdapter
import com.example.climafilm.util.Constants.Companion.BASE_IMAGE_URL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MoviePagerAdapter @Inject constructor(
    private val requestManager: RequestManager
) : BaseMovieAdapter<Movie>() {
    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return MovieItemViewPagerBinding.inflate(inflater, parent, false)
    }

    override fun bind(holder: ViewHolder, movie: Movie) {
        val binding = holder.binding as MovieItemViewPagerBinding
        val genreName = mapGenreIdsToNames(movie.genre_ids).first()

        binding.imageMovieName.text = movie.title
        binding.releaseDate.text = getFormattedDate(movie)
        binding.genre.text = genreName
        binding.voteAverage.text = movie.vote_average.toString()
        binding.voteCount.text = movie.vote_count.toString()
        requestManager.load(BASE_IMAGE_URL + movie.backdrop_path)
            .into(binding.backGroundImageMovie)
        requestManager.load(BASE_IMAGE_URL + movie.poster_path).into(binding.imageMovie)
    }

    override fun getItemCount(): Int = movieList.size / 2

    private fun getFormattedDate(movie: Movie): String {
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
}

