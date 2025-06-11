package com.example.climafilm.presentation.features.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.climafilm.R

@Composable
fun OnboardingScreen(
    imageResourceId: Int,
    onBoardingTitleTextResourceId: Int,
    onBoardingContentTextResourceId: Int
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .height(screenHeight * 0.5f),
                contentScale = ContentScale.Crop
            )

            Text(
                text = stringResource(id = onBoardingTitleTextResourceId),
                fontSize = 14.sp,
                lineHeight = 15.sp,
                fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Text(
                text = stringResource(id = onBoardingContentTextResourceId),
                fontSize = 14.sp,
                lineHeight = 25.sp,
                fontFamily = FontFamily(Font(R.font.quicksand_bold)),
                modifier = Modifier
                    .padding(top = 25.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    OnboardingScreen(
        R.string.welcome_description,
        R.string.welcome_description,
        R.string.welcome_description
    )
}