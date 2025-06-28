package com.practicum.playlismaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlismaker.ui.THEME_KEY
import com.practicum.playlismaker.ui.getPrefs

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        switchTheme(getPrefs().getBoolean(THEME_KEY, false))
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}
