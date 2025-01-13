package com.practicum.playlismaker

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

const val THEME_KEY = "THEME_KEY"

fun Context.getPrefs(): SharedPreferences {
    return getSharedPreferences(
        "practicum_example_preferences",
        AppCompatActivity.MODE_PRIVATE
    )
}