package com.practicum.playlismaker.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlismaker.domain.models.Track
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

fun getCoverArtwork(track: Track) = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")

private const val CLICK_DEBOUNCE_DELAY = 1000L
private var isClickAllowed = true
private val handler = Handler(Looper.getMainLooper())

fun clickDebounce(): Boolean {
    val current = isClickAllowed
    if (isClickAllowed) {
        isClickAllowed = false
        handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
    }
    return current
}

class SearchDebounce(private val searchRequest: ()-> Unit){

    private val searchRunnable = Runnable { searchRequest() }

    fun run() {
        clear()
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun clear() {
        handler.removeCallbacks(searchRunnable)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
