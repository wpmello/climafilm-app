package com.example.climafilm.domain.enums

import android.content.Context
import com.example.climafilm.R

enum class TemperatureUnit(val unitNameResourceId: Int) {
    CELSIUS(R.string.celsius_temperature);

    companion object {
        @JvmStatic
        fun TemperatureUnit.displayName(context: Context): String = when (this) {
            CELSIUS -> context.getString(R.string.celsius_temperature)
        }
    }
}