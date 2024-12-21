package com.example.climafilm.util

import java.text.SimpleDateFormat
import java.util.*

class CommonComponents {
    companion object {
        fun getFormattedDate(date: String): String {
            val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val desiredFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            return try {
                val date = originalFormat.parse(date)
                date?.let { desiredFormat.format(it) }.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                "Something went wrong"
            }
        }
    }
}