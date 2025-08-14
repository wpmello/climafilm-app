package com.example.climafilm.domain.enums

import android.content.Context
import com.example.climafilm.R

enum class Language(val languageNameResourceId: Int) {
    PORTUGUESE(R.string.portuguese_language);

    companion object {
        @JvmStatic
        fun Language.displayName(context: Context): String? = when (this) {
            PORTUGUESE -> context.getString(R.string.portuguese_language)
        }
    }
}