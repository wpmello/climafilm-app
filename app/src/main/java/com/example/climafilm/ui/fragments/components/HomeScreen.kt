package com.example.climafilm.ui.fragments.components

import androidx.annotation.StringRes
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.climafilm.data.model.Poster
import com.example.climafilm.domain.enums.mapGenreIdsToNames
import com.example.climafilm.domain.model.Movie
import com.example.climafilm.ui.viewmodels.home.HomeViewModel
import com.example.climafilm.ui.viewmodels.home.PopularMoviesViewModel
import com.example.climafilm.ui.viewmodels.home.TopRatedMoviesViewModel
import com.example.climafilm.ui.viewmodels.home.UpcomingMoviesViewModel
import com.example.climafilm.util.CommonComponents
import com.example.climafilm.util.Constants
import com.example.climafilm.util.Resource
import kotlinx.coroutines.launch

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.now_playing_movies),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

//        ViewPagerWithTabs(modifier = Modifier.height(250.dp), pageCount = nowPlayingMovies.data?.results?.size ?: 0) { page ->
//            val movie = nowPlayingMovies.data?.results?.get(page)?.toEntity()
//            movie?.let {
//                MoviePage(movie = it, onClick = { onNavigateToMovieDetail(it.id) })
//            }
//        }

        val pagerState =
            rememberPagerState(initialPage = 0) { nowPlayingMovies.data?.results?.size ?: 0 }
        HorizontalPager(state = pagerState, modifier = Modifier.height(250.dp)) { page ->
            val movie = nowPlayingMovies.data?.results?.get(page)?.toEntity()
            movie?.let {
                MoviePage(movie = it, onClick = { onNavigateToMovieDetail(it.id) })
            }
        }

        MovieSection(
            title = R.string.popular_movies,
            moviesResource = popularMovies,
            onClick = onNavigateToMovieDetail
        )
        MovieSection(
            title = R.string.top_rated_movies,
            moviesResource = topRatedMovies,
            onClick = onNavigateToMovieDetail
        )
        MovieSection(
            title = R.string.upcoming_movies,
            moviesResource = upcomingMovies,
            onClick = onNavigateToMovieDetail
        )
    }
}


@Composable
fun ViewPagerWithTabs(
    modifier: Modifier = Modifier,
    pageCount: Int,
    content: @Composable (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color.Black)
        ) { page ->
            content(page)
        }

        CustomTabLayout(
            pagerState = pagerState,
            onTabSelected = { index ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        )
    }
}

@Composable
fun CustomTabLayout(
    pagerState: PagerState,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        backgroundColor = Color.Transparent,
        indicator = { tabPositions ->
            androidx.compose.material3.TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .clip(CircleShape)
                    .size(8.dp),
                color = Color.White
            )
        },
        divider = {}
    ) {
        repeat(pagerState.pageCount) { index ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(8.dp)
                    .clip(CircleShape),
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) Color.White else Color.Gray)
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
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Constants.BASE_IMAGE_URL + movie.backdrop_path)
                .crossfade(true)
                .build(),
            contentDescription = "Movie Poster",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.3f)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = movie.title ?: "The best movie of the world",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Card(
                    modifier = Modifier.size(150.dp, 200.dp),
                    shape = RoundedCornerShape(9.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(Constants.BASE_IMAGE_URL + movie.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Movie Thumbnail",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = mapGenreIdsToNames(movie.genre_ids ?: listOf())
                            .joinToString(", "),
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = movie.vote_count.toString(),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Like",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(26.dp)
                                .padding(4.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.ThumbDown,
                            contentDescription = "Dislike",
                            tint = Color.White,
                            modifier = Modifier
                                .size(26.dp)
                                .padding(4.dp)
                        )
                    }
                    Text(
                        text = "${movie.vote_average}/10",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(4.dp)
                    )
                    Text(
                        text = CommonComponents.getFormattedDate(movie.release_date),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MovieSection(
    @StringRes title: Int,
    moviesResource: Resource<Poster>? = null,
    onClick: (Int) -> Unit
) {
    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(title),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        when (moviesResource) {
            is Resource.Success -> {
                LazyRow(modifier = Modifier.padding(top = 8.dp)) {
                    items(moviesResource.data?.results?.map { it.toEntity() }
                        ?: emptyList()) { movie ->
                        MovieItem(movie, onClick = { onClick(movie.id) })
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = moviesResource.message ?: stringResource(R.string.error_loading_movies),
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieItem(movie: Movie, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp, 180.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .width(100.dp)
                .padding(4.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp),
                shape = RoundedCornerShape(9.dp),
                onClick = onClick
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Constants.BASE_IMAGE_URL + movie.poster_path)
                        .crossfade(true)
                        .build(),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = movie.title ?: "The best movie of the world",
                color = Color.White,
                fontSize = 11.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

//@Preview
//@Composable
//private fun CardScreen() {
//    HomeScreen({ })
//}

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
    MoviePage(movie) {}
}