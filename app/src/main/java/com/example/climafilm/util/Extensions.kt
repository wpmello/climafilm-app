package com.example.climafilm.util

fun Int.formatMinutesToHoursAndMinutes(): String {
    val hours = this / 60
    val minutes = this % 60

    return if (hours > 0) {
        if (minutes > 0) {
            "$hours h $minutes m"
        } else {
            "$hours h"
        }
    } else {
        "$minutes m"
    }
}