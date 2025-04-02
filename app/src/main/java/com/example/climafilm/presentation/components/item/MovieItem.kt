package com.example.climafilm.presentation.components.item

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.presentation.components.card.MovieCard
import com.example.climafilm.presentation.components.title.SimpleTitle


@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Column(modifier = Modifier.width(100.dp)) {
            MovieCard(width = 100, height = 150, movie = movie, onClick = onClick)
            SimpleTitle(title = movie.title, fontSize = 12)
        }
    }
}