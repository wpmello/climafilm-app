package com.example.climafilm.presentation.viewmodels.clima

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import com.example.climafilm.R
import com.example.climafilm.data.source.remote.model.movie.MovieResponse
import com.example.climafilm.domain.usecase.GetMoviesPerCityUseCase
import com.example.climafilm.presentation.viewmodels.base.BaseViewModel
import com.example.climafilm.util.Resource
import com.example.climafilm.util.map
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MoviesPerCityViewModel @Inject constructor(
    private val getMoviesPerCityUseCase: GetMoviesPerCityUseCase
) : BaseViewModel<List<MovieResponse>>() {
    private val _categorizedMovies = MutableStateFlow<Map<Int, List<MovieResponse>>>(emptyMap())
    var categorizedMovies: StateFlow<Map<Int, List<MovieResponse>>> = _categorizedMovies
    private val _cityName = MutableStateFlow("")
    var cityName: StateFlow<String> = _cityName
    private val _temp = MutableStateFlow(0)
    var temp: StateFlow<Int> = _temp

    override fun fetchMovies() {}

    fun search(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.value = Resource.Loading()
            val movieResponse = getMoviesPerCityUseCase.invoke(city.trim())
            _movie.value =
                handleResponse(movieResponse).map { it?.values?.flatten() ?: emptyList() }
            categorizeMovies()
            _cityName.value = city
            _temp.value = movieResponse.body()?.keys?.first()?.toInt() ?: 0
        }
    }

    private fun categorizeMovies() {
        val allMovies = _movie.value.data

        if (allMovies.isNullOrEmpty()) {
            _categorizedMovies.value = emptyMap()
            return
        }

        val categories = allMovies.flatMap { it.genre_ids }.toSet()

        if (categories.isEmpty()) {
            _categorizedMovies.value = emptyMap()
            return
        }

        _categorizedMovies.value = categories.associateWith { category ->
            allMovies.filter { movie ->
                movie.genre_ids.contains(category)
            }
        }.filterValues { it.isNotEmpty() }
    }

    fun getCurrentCity(context: Context, onCityResolved: (String) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val hasFine = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val randomIndex = Random.nextInt(0, 6)
        val continents = listOf(
            context.getString(R.string.africa),
            context.getString(R.string.america),
            context.getString(R.string.antarctica),
            context.getString(R.string.asia),
            context.getString(R.string.europa),
            context.getString(R.string.oceania)
        )

        if (!hasFine && !hasCoarse) {
            onCityResolved(continents[randomIndex])
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                resolveCityFromLocation(
                    context,
                    location.latitude,
                    location.longitude,
                    onCityResolved
                )
            } else {
                val locationRequest = LocationRequest.Builder(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    1000
                ).setMaxUpdates(1).build()

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(result: LocationResult) {
                            val updatedLocation = result.lastLocation
                            if (updatedLocation != null) {
                                resolveCityFromLocation(
                                    context,
                                    updatedLocation.latitude,
                                    updatedLocation.longitude,
                                    onCityResolved
                                )
                            } else {
                                onCityResolved(continents[randomIndex])
                            }
                            fusedLocationClient.removeLocationUpdates(this)
                        }
                    },
                    Looper.getMainLooper()
                )
            }
        }.addOnFailureListener {
            onCityResolved(continents[randomIndex])
        }
    }

    private fun resolveCityFromLocation(
        context: Context,
        latitude: Double,
        longitude: Double,
        onCityResolved: (String) -> Unit
    ) {
        val randomIndex = Random.nextInt(0, 6)
        val continents = listOf(
            context.getString(R.string.africa),
            context.getString(R.string.america),
            context.getString(R.string.antarctica),
            context.getString(R.string.asia),
            context.getString(R.string.europa),
            context.getString(R.string.oceania)
        )

        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            // Todo: resolve deprecated
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            val address = addresses?.firstOrNull()

            val city = address?.locality
                ?: address?.subAdminArea
                ?: address?.adminArea
                ?: address?.countryName
                ?: continents[randomIndex]

            onCityResolved(city)

        } catch (e: Exception) {
            onCityResolved(continents[randomIndex])
        }
    }
}