package com.practicum.playlismaker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val backButton = findViewById<View>(R.id.settings_back)
        backButton.setOnClickListener{
            onBackPressed()
        }
    }
}