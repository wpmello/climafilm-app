package com.example.climafilm.presentation.components.list

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climafilm.R
import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.presentation.components.item.MovieItem
import com.example.climafilm.presentation.components.title.SimpleTitle
import com.example.climafilm.util.Resource

@Composable
fun MovieList(
    @StringRes title: Int,
    moviesResource: Resource<Poster>,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        SimpleTitle(
            title = stringResource(title),
            fontSize = 20,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )

        when (moviesResource) {
            is Resource.Success -> {
                MovieSection(
                    movies = moviesResource.data?.results?.map { it.toEntity() } ?: emptyList(),
                    onClick = onClick
                )
            }

            is Resource.Error -> {
                Text(
                    text = moviesResource.message ?: stringResource(R.string.error_loading_movies),
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun MovieSection(modifier: Modifier = Modifier, movies: List<Movie> = emptyList(), onClick: (Int) -> Unit) {
    LazyRow(modifier = Modifier.padding(top = 8.dp)) {
        items(movies) { movie ->
            MovieItem(movie, onClick = { onClick(movie.id) })
        }
    }
}