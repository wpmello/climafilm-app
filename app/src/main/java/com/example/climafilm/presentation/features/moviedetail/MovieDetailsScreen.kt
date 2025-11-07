package com.example.climafilm.presentation.features.moviedetail

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.climafilm.R
import com.example.climafilm.data.source.remote.model.movie.detail.GenreResponse
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.domain.model.MovieDetail
import com.example.climafilm.presentation.viewmodels.moviedetail.MovieDetailViewModel
import com.example.climafilm.util.CommonComponents
import com.example.climafilm.util.Constants
import com.example.climafilm.util.Resource
import com.example.climafilm.util.formatCurrency
import com.example.climafilm.util.formatMinutesToHoursAndMinutes
import com.example.climafilm.util.formatNumber
import java.util.Locale

@Composable
fun MovieDetailsScreen(
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
            val message =
                if (!(movieDetail as Resource.Error).message.isNullOrEmpty()) (movieDetail as Resource.Error).message
                else stringResource(R.string.error_loading_movies)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.onBackground
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.BASE_IMAGE_URL + movie.backdrop_path)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.background_movie),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.Transparent)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onBackPressed,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back),
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
                Card(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 60.dp)
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
                        contentDescription = stringResource(R.string.movie_poster),
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
                    color = MaterialTheme.colorScheme.onBackground,
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
                DetailItem(
                    icon = R.drawable.ic_star_rate,
                    text = String.format(Locale.getDefault(), "%.1f", movie.vote_average)
                )
                DetailItem(
                    icon = R.drawable.ic_calendar,
                    text = CommonComponents.getFormattedDate(movie.release_date)
                )
                DetailItem(
                    icon = R.drawable.ic_access_time,
                    text = movie.runtime?.formatMinutesToHoursAndMinutes()
                )
            }
            Text(
                text = movie.overview,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)
            )
            InformationBox(movie = movie)
        }
    }
}

@Composable
fun DetailItem(icon: Int, text: String?) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = if (!text.isNullOrEmpty() || !text.isNullOrBlank()) text else "\uD83D\uDEDC",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun InformationBox(movie: MovieDetail) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp, horizontal = 16.dp)
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            InfoItem(
                title = stringResource(R.string.genres),
                content = mapGenreIdsToNames(movie.genres?.map { it.id }, LocalContext.current)
                    ?.joinToString(", ")
            )
            InfoItem(title = stringResource(R.string.tag_line), content = movie.tagline)
            InfoItem(
                title = stringResource(R.string.vote_count),
                content = movie.vote_count.formatNumber()
            )
            InfoItem(
                title = stringResource(R.string.budget),
                content = movie.budget?.formatCurrency()
            )
            InfoItem(
                title = stringResource(R.string.revenue),
                content = movie.revenue?.formatCurrency()
            )
        }
    }
}

@Composable
fun InfoItem(title: String, content: String?) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(
            text = if(!content.isNullOrEmpty() || !content.isNullOrBlank()) content else "Informação não disponibilizada pelo serviço ou necessário acesso a internet! \uD83D\uDEDC",
            color = Color.White, fontSize = 12.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    val movie = MovieDetail(
        id = 1,
        title = "Movie Title",
        poster_path = "/path/to/poster",
        genres = listOf(GenreResponse(1, "name 1"), GenreResponse(1, "name 1"), GenreResponse(1, "name 1")),
        vote_count = 100,
        vote_average = 7.5,
        release_date = "2023-09-05",
        overview = "Movie overview",
        tagline = "Movie tagline",
        budget = 1000000,
        revenue = 2000000,
        backdrop_path = "/path/to/backdrop",
        runtime = 120
    )
//    MovieDetailScreen(movie.id)
    MovieDetailContent(movie) { }
}