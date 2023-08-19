package com.example.climafilm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import com.example.climafilm.carousel.CarouselMovieAdapter
import com.example.climafilm.databinding.FragmentHomeBinding
import com.example.climafilm.model.Movie

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val movieList = mutableListOf(
            Movie("Primeira", R.drawable.introduction_image),
            Movie("Segunda", R.drawable.introduction_image_ia_helper),
            Movie("Terceira", R.drawable.welcome_image)
        )

        val adapter = CarouselMovieAdapter(movieList)

        binding.apply {
            carouselRecyclerView.adapter = adapter
            carouselRecyclerView.set3DItem(true)
            carouselRecyclerView.setAlpha(true)
            carouselRecyclerView.setInfinite(true)
            carouselRecyclerView.size
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}