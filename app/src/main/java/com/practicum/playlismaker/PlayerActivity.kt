package com.practicum.playlismaker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val image = findViewById<ImageView>(R.id.player_artwork)
        val title = findViewById<TextView>(R.id.player_title)
        val subtitle = findViewById<TextView>(R.id.player_subtitle)
        val btnAdd = findViewById<View>(R.id.player_btn_add)
        val btnFavorite = findViewById<View>(R.id.player_btn_favorite)
        val btnPlay = findViewById<View>(R.id.player_btn_play)
        val trackTime = findViewById<TextView>(R.id.player_track_time)
        val trackInfoTime = findViewById<TextView>(R.id.player_track_info_time)
        val trackInfoAlbumTitle = findViewById<TextView>(R.id.player_track_info_album_title)
        val trackInfoAlbum = findViewById<TextView>(R.id.player_track_info_album)
        val trackInfoYear = findViewById<TextView>(R.id.player_track_info_year)
        val trackInfoGenre = findViewById<TextView>(R.id.player_track_info_genre)
        val trackInfoCountry = findViewById<TextView>(R.id.player_track_info_country)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val trackId = intent.extras!!.getLong(TRACK_ID_KEY)
        val track = SearchHistory(getPrefs()).getHistory().find { it.trackId == trackId }!!

        Glide.with(this)
            .load(getCoverArtwork(track))
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(8f, this)))
            .into(image)

        title.text = track.trackName
        subtitle.text = track.artistName
        trackTime.text = "0:00"
        trackInfoTime.text = formatTime(track)
        val collectionName = track.collectionName
        if (collectionName != null) {
            trackInfoAlbumTitle.visibility = View.VISIBLE
            trackInfoAlbum.visibility = View.VISIBLE
            trackInfoAlbum.text = collectionName
        } else {
            trackInfoAlbumTitle.visibility = View.GONE
            trackInfoAlbum.visibility = View.GONE
        }
        trackInfoGenre.text = track.primaryGenreName
        trackInfoCountry.text = track.country
        trackInfoYear.text = formatYear(track)
    }

    companion object {

        private const val TRACK_ID_KEY = "TRACK_ID_KEY"

        fun newInstance(context: Context, trackId: Long): Intent {
            val intent = Intent(context, PlayerActivity::class.java).apply {
                putExtra(TRACK_ID_KEY, trackId)
            }
            return intent
        }
    }
}