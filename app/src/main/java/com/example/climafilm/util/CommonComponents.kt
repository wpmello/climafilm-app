package com.example.climafilm.util

import java.text.SimpleDateFormat
import java.util.*

class CommonComponents {
    companion object {
        fun getFormattedDate(date: String? = null): String {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val desiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            return try {
                val date = date?.let { originalFormat.parse(it) }
                date?.let { desiredFormat.format(it) }.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                "Something went wrong"
            }
        }

        fun getGenresMovieDescriptionPerTemp(temp: Int): String {
            //TODO: resource strings
            return if (temp >= 38) {
                "Nos estados/países com a temperatura 38°C ou mais são listados filmes que contém o gênero de animação";
            } else if (temp > 30) {
                "Nos estados/países com a temperatura entre 31°C e 38° são listados filmes que contém o gênero de ação";
            } else if (temp > 20) {
                "Nos estados/países com a temperatura entre 21°C e 30°C são listados filmes que contém o gênero de comédia";
            } else if (temp >= 0) {
                "Nos estados/países com a temperatura entre 0°C e 20°C são listados filmes que contém o gênero de suspense";
            } else {
                "Nos estados/países com a temperatura 0ºC ou menos são listados filmes que contém o gênero de documentário";
            }
        }
    }
}