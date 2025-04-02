package com.example.climafilm.presentation.components.title

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SimpleTitle(
    modifier: Modifier = Modifier,
    title: String?,
    fontSize: Int = 16,
    color: Color = Color.White,
    padding: Int = 8,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        text = title ?: "",
        color = color,
        fontSize = fontSize.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = padding.dp),
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}