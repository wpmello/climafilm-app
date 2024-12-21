package com.example.climafilm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.climafilm.data.model.detail.MovieDetail
import com.example.climafilm.databinding.FragmentMovieDetailBinding
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.ui.viewmodels.MovieDetailViewModel
import com.example.climafilm.util.CommonComponents
import com.example.climafilm.util.Constants
import com.example.climafilm.util.Resource
import com.example.climafilm.util.formatMinutesToHoursAndMinutes
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieDetailViewModel by viewModels()
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        viewModel.initialize(args.movieId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            when (movie) {
                is Resource.Success -> {
                    movie.data?.let {
                        setUp(it)
                        binding.progressBar.visibility = View.GONE
                    }
                }
                is Resource.Error -> {
                    movie.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        binding.backButton.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setUp(movieDetail: MovieDetail) {
        binding.apply {
            Glide.with(this.backgroundMovie)
                .load(Constants.BASE_IMAGE_URL + movieDetail.backdrop_path)
                .into(this.backgroundMovie)
            Glide.with(this.imageMovie)
                .load(Constants.BASE_IMAGE_URL + movieDetail.poster_path)
                .into(this.imageMovie)
            movieTitle.text = movieDetail.title
            rating.text = String.format("%.1f", movieDetail.vote_average)
            releaseDate.text = CommonComponents.getFormattedDate(movieDetail.release_date)
            runtime.text = movieDetail.runtime.formatMinutesToHoursAndMinutes()
            description.text = movieDetail.overview
            val genreIds = movieDetail.genres.map { it.id }
            genresContent.text = mapGenreIdsToNames(genreIds).joinToString(", ")
            taglineContent.text = movieDetail.tagline
            voteCountContent.text = movieDetail.vote_count.toString()
            val format = NumberFormat.getCurrencyInstance(Locale.US)
            budgetContent.text = format.format(movieDetail.budget)
            revenueContent.text = format.format(movieDetail.revenue)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}