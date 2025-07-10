package com.practicum.playlismaker.ui.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlismaker.R
import com.practicum.playlismaker.data.SearchHistory
import com.practicum.playlismaker.ui.dpToPx
import com.practicum.playlismaker.ui.formatTime
import com.practicum.playlismaker.ui.formatYear
import com.practicum.playlismaker.ui.getCoverArtwork
import com.practicum.playlismaker.ui.getPrefs

class PlayerActivity : AppCompatActivity() {

    private var player: Player? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val image = findViewById<ImageView>(R.id.player_artwork)
        val title = findViewById<TextView>(R.id.player_title)
        val subtitle = findViewById<TextView>(R.id.player_subtitle)
        val btnAdd = findViewById<View>(R.id.player_btn_add)
        val btnFavorite = findViewById<View>(R.id.player_btn_favorite)
        val btnPlay = findViewById<ImageView>(R.id.player_btn_play)
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
        val sharedPrefs = getPrefs()
        val track = SearchHistory(sharedPrefs).getHistory().find { it.trackId == trackId }!!
        player = Player(
            track,
            sharedPrefs,
            positionUpdateCallback = { trackTime.text = it },
            stateUpdateCallback = {
                Log.d("TAG", "onCreate: player state $it")
                when (it) {
                    Player.State.STATE_PREPARED -> {
                        btnPlay.setImageResource(R.drawable.btn_player_play)
                        btnPlay.isEnabled = true
                    }
                    Player.State.STATE_PLAYING ->
                        btnPlay.setImageResource(R.drawable.btn_player_pause)
                    Player.State.STATE_PAUSED ->
                        btnPlay.setImageResource(R.drawable.btn_player_play)
                    else -> {}
                }
            }
        )
        player?.preparePlayer()

        Glide.with(this)
            .load(getCoverArtwork(track))
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(8f, this)))
            .into(image)

        title.text = track.trackName
        subtitle.text = track.artistName
        trackTime.text = getString(R.string.player_zero_time)
        trackInfoTime.text = track.trackTime
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
        btnPlay.setOnClickListener { player?.playbackControl() }
        btnPlay.isEnabled = false
    }

    override fun onPause() {
        super.onPause()
        player?.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
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