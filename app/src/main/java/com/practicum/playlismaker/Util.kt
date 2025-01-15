package com.practicum.playlismaker

import android.content.Context
import android.content.SharedPreferences
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Locale

const val THEME_KEY = "THEME_KEY"

fun Context.getPrefs(): SharedPreferences {
    return getSharedPreferences(
        "practicum_example_preferences",
        AppCompatActivity.MODE_PRIVATE
    )
}

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}

fun formatTime(model: Track): String? =
    SimpleDateFormat("mm:ss", Locale.getDefault()).format(model.trackTimeMillis)

fun formatYear(model: Track): String? {
    val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
    val parse = simpleDateFormat.parse(model.releaseDate)
    return simpleDateFormat.format(parse)
}

fun getCoverArtwork(track: Track) = track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg")