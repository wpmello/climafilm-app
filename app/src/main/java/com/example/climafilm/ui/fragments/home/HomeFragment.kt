package com.example.climafilm.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.climafilm.databinding.FragmentHomeBinding
import com.example.climafilm.ui.adapters.home.MoviePagerAdapter
import com.example.climafilm.ui.adapters.home.PopularMoviesAdapter
import com.example.climafilm.ui.adapters.home.TopRatedMoviesAdapter
import com.example.climafilm.ui.adapters.home.UpcomingMoviesAdapter
import com.example.climafilm.ui.viewmodels.home.HomeViewModel
import com.example.climafilm.ui.viewmodels.home.PopularMoviesViewModel
import com.example.climafilm.ui.viewmodels.home.TopRatedMoviesViewModel
import com.example.climafilm.ui.viewmodels.home.UpcomingMoviesViewModel
import com.example.climafilm.util.Resource
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val popularViewModel: PopularMoviesViewModel by viewModels()
    private val topRatedViewModel: TopRatedMoviesViewModel by viewModels()
    private val upcomingViewModel: UpcomingMoviesViewModel by viewModels()

    @Inject
    lateinit var pagerAdapter: MoviePagerAdapter

    @Inject
    lateinit var popularAdapter: PopularMoviesAdapter

    @Inject
    lateinit var topRatedAdapter: TopRatedMoviesAdapter

    @Inject
    lateinit var upcomingAdapter: UpcomingMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.movieList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { movieResponse ->
                        pagerAdapter.setList(movieResponse.results.map { it.toEntity() })
                        binding.viewPager2.adapter = pagerAdapter
                        TabLayoutMediator(
                            binding.tabLayout,
                            binding.viewPager2
                        ) { tab, position -> }.attach()
                        binding.progressViewPager2.visibility = View.GONE
                        pagerAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressViewPager2.visibility = View.VISIBLE
                }
            }
        }

        popularViewModel.movieList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { movieResponse ->
                        popularAdapter.setList(movieResponse.results.map { it.toEntity() })
                        binding.rvPopularMovies.adapter = popularAdapter
                        popularAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        }

        topRatedViewModel.movieList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { movieResponse ->
                        topRatedAdapter.setList(movieResponse.results.map { it.toEntity() })
                        binding.rvTopRatedMovies.adapter = topRatedAdapter
                        topRatedAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        }

        upcomingViewModel.movieList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { movieResponse ->
                        upcomingAdapter.setList(movieResponse.results.map { it.toEntity() })
                        binding.rvUpcomingMovies.adapter = upcomingAdapter
                        upcomingAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}