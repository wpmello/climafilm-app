package com.example.climafilm.domain.enums

import android.content.Context
import com.example.climafilm.R

enum class AppThemeOption(val appThemeOptionsNameResourceId: Int) {
    SYSTEM(R.string.system),
    LIGHT(R.string.light),
    DARK(R.string.dark);

    companion object {
        @JvmStatic
        fun AppThemeOption.displayName(context: Context): String = when (this) {
            LIGHT -> context.getString(R.string.light)
            DARK -> context.getString(R.string.dark)
            SYSTEM -> context.getString(R.string.system)
        }
    }
}