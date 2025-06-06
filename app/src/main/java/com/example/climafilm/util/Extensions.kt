package com.example.climafilm.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.text.NumberFormat
import java.util.Locale

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

fun Int.formatNumber(): String {
    return NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
}

fun Int.formatCurrency(): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
    return currencyFormat.format(this)
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
