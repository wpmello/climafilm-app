package com.example.climafilm.presentation.features.clima

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafilm.R
import com.example.climafilm.data.model.MovieResponse
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.presentation.components.item.MovieItem
import com.example.climafilm.presentation.components.permission.RequestLocationPermission
import com.example.climafilm.presentation.components.searchbar.SearchBarIU
import com.example.climafilm.presentation.components.title.SimpleTitle
import com.example.climafilm.presentation.viewmodels.clima.MoviesPerCityViewModel
import com.example.climafilm.presentation.viewmodels.onboarding.OnBoardingPreferences
import com.example.climafilm.util.CommonComponents.Companion.getGenresMovieDescriptionPerTemp
import com.example.climafilm.util.Resource
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ClimaScreen(onNavigateToMovieDetail: (Int) -> Unit) {
    val viewModel: MoviesPerCityViewModel = hiltViewModel()
    val categorizedMovies by viewModel.categorizedMovies.collectAsState()
    val movieState by viewModel.movie.collectAsState()
    val context = LocalContext.current

    var query by remember { mutableStateOf("") }
    var locationInitialized by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val hasSeen = OnBoardingPreferences.hasSeenDialog(context)
        showDialog = !hasSeen
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                scope.launch {
                    OnBoardingPreferences.setHasSeenDialog(context, true)
                    showDialog = false
                }
            },
            title = { Text(stringResource(R.string.give_permission)) },
            text = { Text(stringResource(R.string.give_permission_content)) },
            confirmButton = {
                Button(onClick = {
                    scope.launch {
                        OnBoardingPreferences.setHasSeenDialog(context, true)
                        showDialog = false
                    }
                }) {
                    Text(stringResource(R.string.finish))
                }
            }
        )
    }

    RequestLocationPermission(
        onRequestLocationFinished = {
            if (!locationInitialized) {
                viewModel.getCurrentCity(context) { city ->
                    viewModel.search(city)
                    query = city
                }
                locationInitialized = true
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        SearchBarIU(
            query = query,
            onQueryChange = { query = it },
            onSearch = { searchTerm -> viewModel.search(searchTerm) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.WbSunny, contentDescription = "Buscar")
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Limpar")
                    }
                }
            },
            placeholder = "Digite um País/Estado..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${viewModel.cityName.value.trim()}, ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${viewModel.temp.value}°C",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = getGenresMovieDescriptionPerTemp(viewModel.temp.value),
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        when (movieState) {
            is Resource.Success -> CategorizedMovies(categorizedMovies, onNavigateToMovieDetail)
            is Resource.Error -> Text(
                text = stringResource(R.string.error_loading_movies),
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            is Resource.Loading -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colors.primary
            )
        }
    }
}

@Composable
fun CategorizedMovies(
    categorizedMovies: Map<Int, List<MovieResponse>>,
    onNavigateToMovieDetail: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        categorizedMovies.forEach { (genreId, movies) ->
            item {
                Column {
                    val genreName = mapGenreIdsToNames(listOf(genreId)).joinToString()
                    SimpleTitle(
                        title = genreName,
                        fontSize = 20,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(movies) { movie ->
                            MovieItem(
                                movie = movie.toEntity(),
                                onClick = { onNavigateToMovieDetail(movie.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClimaScreenPreview() {
    ClimaScreen({})
}