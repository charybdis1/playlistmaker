package com.practicum.playlismaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        val share = findViewById<View>(R.id.settings_share)
        share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_share_link_practicum))
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        val support = findViewById<View>(R.id.settings_support)
        support.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto:"))
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.settings_share_email)))
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.settings_share_subject))
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.settings_share_text))
            startActivity(intent)
        }
        val agreement = findViewById<View>(R.id.settings_agreement)
        agreement.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(getString(R.string.settings_share_link_agreement))
            startActivity(intent)
        }
    }
}