package com.example.climafilm.presentation.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.climafilm.R
import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.presentation.components.card.MovieCard
import com.example.climafilm.presentation.components.list.MovieList
import com.example.climafilm.presentation.components.title.SimpleTitle
import com.example.climafilm.presentation.viewmodels.home.HomeViewModel
import com.example.climafilm.presentation.viewmodels.home.PopularMoviesViewModel
import com.example.climafilm.presentation.viewmodels.home.TopRatedMoviesViewModel
import com.example.climafilm.presentation.viewmodels.home.UpcomingMoviesViewModel
import com.example.climafilm.util.CommonComponents
import com.example.climafilm.util.Resource
import java.util.Locale

@Composable
fun HomeScreen(onNavigateToMovieDetail: (Int) -> Unit) {
    val nowPlayingViewModel: HomeViewModel = hiltViewModel()
    val popularMoviesViewModel: PopularMoviesViewModel = hiltViewModel()
    val topRatedMoviesViewModel: TopRatedMoviesViewModel = hiltViewModel()
    val upcomingMoviesViewModel: UpcomingMoviesViewModel = hiltViewModel()

    val nowPlayingMovies by nowPlayingViewModel.movie.collectAsState()
    val popularMovies by popularMoviesViewModel.movie.collectAsState()
    val topRatedMovies by topRatedMoviesViewModel.movie.collectAsState()
    val upcomingMovies by upcomingMoviesViewModel.movie.collectAsState()

    HomeContent(nowPlayingMovies = nowPlayingMovies,
        popularMovies = popularMovies,
        topRatedMovies = topRatedMovies,
        upcomingMovies = upcomingMovies,
        onNavigateToMovieDetail = onNavigateToMovieDetail
    )
}

@Composable
fun HomeContent(
    nowPlayingMovies: Resource<Poster>,
    popularMovies: Resource<Poster>,
    topRatedMovies: Resource<Poster>,
    upcomingMovies: Resource<Poster>,
    onNavigateToMovieDetail: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        NowPlayingSection(nowPlayingMovies = nowPlayingMovies, onNavigateToMovieDetail = onNavigateToMovieDetail)

        MovieList(
            title = R.string.popular_movies,
            moviesResource = popularMovies,
            onClick = onNavigateToMovieDetail
        )
        MovieList(
            title = R.string.top_rated_movies,
            moviesResource = topRatedMovies,
            onClick = onNavigateToMovieDetail
        )
        MovieList(
            title = R.string.upcoming_movies,
            moviesResource = upcomingMovies,
            onClick = onNavigateToMovieDetail
        )
    }
}


@Composable
fun NowPlayingSection(
    nowPlayingMovies: Resource<Poster>,
    onNavigateToMovieDetail: (Int) -> Unit
) {
    Column {
        SimpleTitle(
            title = stringResource(R.string.now_playing_movies),
            fontSize = 20,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        when (nowPlayingMovies) {
            is Resource.Success -> {
                val movies = nowPlayingMovies.data?.results?.map { it.toEntity() } ?: emptyList()
                val pagerState = rememberPagerState(initialPage = 0) { movies.size }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.height(250.dp)
                ) { page ->
                    val movie = movies[page]
                    MoviePage(movie = movie, onClick = { onNavigateToMovieDetail(movie.id) })
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(movies.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(if (pagerState.currentPage == index) 12.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index) MaterialTheme.colorScheme.primary
                                    else Color.Gray
                                )
                        )
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = nowPlayingMovies.message ?: stringResource(R.string.error_loading_movies),
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
fun MoviePage(movie: Movie, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = movie.title ?: "",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                MovieCard(width = 150, height = 200, movie = movie, onClick = onClick)
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = mapGenreIdsToNames(movie.genre_ids ?: listOf())
                            .joinToString(", "),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                        modifier = Modifier.padding(4.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = movie.vote_count.toString(),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.quicksand_bold))
                        )
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = stringResource(R.string.like),
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(26.dp)
                                .padding(4.dp)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = String.format(Locale.getDefault(),"%.1f", movie.vote_average)+ "/10",
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                            modifier = Modifier
                                .padding(4.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = stringResource(R.string.dislike),
                            tint = Color.Yellow,
                            modifier = Modifier
                                .size(26.dp)
                                .padding(4.dp)
                        )
                    }
                    Text(
                        text = CommonComponents.getFormattedDate(movie.release_date),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.quicksand_bold))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MovieItemPreview() {
    val movie = Movie(
        id = 1,
        title = "Movie Name",
        poster_path = "/path/to/poster",
        genre_ids = listOf(1, 2, 3),
        vote_count = 100,
        vote_average = 7.5,
        release_date = "2023-10-27",
        adult = true,
        backdrop_path = "/path/to/backdrop",
        overview = "Movie overview",
        popularity = 7.8,
        original_title = "Original Movie Name",
    )
    MoviePage(movie) { }
//    MovieItem(movie) {}
//    HomeContent({})
}