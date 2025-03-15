package com.example.climafilm.ui.fragments.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.climafilm.R
import com.example.climafilm.data.model.detail.Genre
import com.example.climafilm.data.model.detail.MovieDetail
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.ui.viewmodels.MovieDetailViewModel
import com.example.climafilm.util.CommonComponents
import com.example.climafilm.util.Constants
import com.example.climafilm.util.Resource
import com.example.climafilm.util.formatCurrency
import com.example.climafilm.util.formatMinutesToHoursAndMinutes
import com.example.climafilm.util.formatNumber

@Composable
fun MovieDetailScreen(
    movieId: Int,
    onBackPressed: () -> Unit,
) {
    val viewModel: MovieDetailViewModel = hiltViewModel()
    val movieDetail by viewModel.movie.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = movieId) {
        viewModel.initialize(movieId)
    }

    when (movieDetail) {
        is Resource.Success -> {
            val movie = (movieDetail as Resource.Success).data
            movie?.let {
                MovieDetailContent(movie = it, onBackPressed)
            }
        }

        is Resource.Error -> {
            val message = (movieDetail as Resource.Error).message ?: "An error occurred"
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MovieDetailContent(
    movie: MovieDetail,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(Constants.BASE_IMAGE_URL + movie.backdrop_path)
                    .crossfade(true)
                    .build(),
                contentDescription = "Background Movie",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .alpha(0.2f)
            )
            IconButton(onClick = onBackPressed, modifier = Modifier.padding(8.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                )
            }
            Card(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(100.dp)
                    .height(150.dp)
                    .align(Alignment.BottomStart),
                shape = RoundedCornerShape(9.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.BASE_IMAGE_URL + movie.poster_path)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DetailItem(icon = R.drawable.ic_star_rate, text = String.format("%.1f", movie.vote_average))
            DetailItem(icon = R.drawable.ic_calendar, text = CommonComponents.getFormattedDate(movie.release_date))
            DetailItem(icon = R.drawable.ic_access_time, text = movie.runtime.formatMinutesToHoursAndMinutes())
        }
        Text(
            text = movie.overview,
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
        )
        InformationBox(movie = movie)
        Text(
            text = "Watch Providers",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp, start = 16.dp)
        )
//        LazyRow(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 8.dp, start = 16.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(watchProviders) { provider ->
//                WatchProviderItem(provider = provider)
//            }
//        }
    }
}

@Composable
fun DetailItem(icon: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Text(text = text, color = Color.White)
    }
}

@Composable
fun InformationBox(movie: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            InfoItem(title = "Gêneros", content = mapGenreIdsToNames(movie.genres.map { it.id })
                .joinToString(", "))
            InfoItem(title = "Slogan", content = movie.tagline)
            InfoItem(title = "Quantidade de votos", content = movie.vote_count.formatNumber())
            InfoItem(title = "Orçamento", content = movie.budget.formatCurrency())
            InfoItem(title = "Receita", content = movie.revenue.formatCurrency())
        }
    }
}

@Composable
fun InfoItem(title: String, content: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = content, color = Color.White, fontSize = 12.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    val movie = MovieDetail(
        id = 1,
        title = "Movie Title",
        poster_path = "/path/to/poster",
        genres = listOf(Genre(1, "name 1"),Genre(1, "name 1"),Genre(1, "name 1")),
        vote_count = 100,
        vote_average = 7.5,
        release_date = "2023-09-05",
        overview = "Movie overview",
        tagline = "Movie tagline",
        budget = 1000000,
        revenue = 2000000,
        backdrop_path = "/path/to/backdrop",
        adult = false,
        belongs_to_collection = Any(),
        homepage = "",
        imdb_id = "",
        original_language = "",
        original_title = "",
        popularity = 0.0,
        production_companies = emptyList(),
        production_countries = emptyList(),
        runtime = 0,
        spoken_languages = emptyList(),
        status = "",
        video = false
    )
//    MovieDetailScreen(movie.id)
    MovieDetailContent(movie) { }
}