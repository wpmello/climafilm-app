package com.example.climafilm.ui.fragments.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.climafilm.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onNavigateToHome: () -> Unit, onNavigateToOnboardingViewPager: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("onBoardingFinished", Context.MODE_PRIVATE)

    fun onBoardingFinished(): Boolean {
        return sharedPreferences.getBoolean("Finished", false)
    }

    LaunchedEffect(Unit) {
        delay(3000L)
        if (onBoardingFinished()) {
            onNavigateToHome.invoke()
        } else {
            onNavigateToOnboardingViewPager.invoke()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_inital_image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f),
            contentScale = ContentScale.Crop
        )

        Text(
            text = stringResource(id = R.string.app_name),
            color = Color.Black,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.quicksand_bold)),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}