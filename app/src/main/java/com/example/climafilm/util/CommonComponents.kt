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
    }
}