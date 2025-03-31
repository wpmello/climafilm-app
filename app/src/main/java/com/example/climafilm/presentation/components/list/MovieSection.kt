package com.example.climafilm.presentation.components.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.presentation.components.item.MovieItem

@Composable
fun MovieSection(modifier: Modifier = Modifier, movies: List<Movie> = emptyList(), onClick: (Int) -> Unit) {
    LazyRow(modifier = Modifier.padding(top = 8.dp)) {
        items(movies) { movie ->
            MovieItem(movie, onClick = { onClick(movie.id) })
        }
    }
}