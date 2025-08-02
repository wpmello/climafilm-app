package com.example.climafilm.presentation.components.card

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.util.Constants

@Composable
fun MovieCard(modifier: Modifier = Modifier, width: Int = 150, height: Int = 200, movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier.size(width.dp, height.dp),
        shape = RoundedCornerShape(9.dp),
        onClick = onClick
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.BASE_IMAGE_URL + movie.poster_path)
                .crossfade(true)
                .build(),
            contentDescription = "${movie.title}",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}