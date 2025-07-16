package com.practicum.playlismaker.ui.player

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.practicum.playlismaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class Player(
    private val track: Track,
    private val sharedPrefs: SharedPreferences,
    private val positionUpdateCallback: (String) -> Unit,
    private val stateUpdateCallback: (State) -> Unit
) {

    companion object {
        private const val POSITION_UPDATE_DELAY_MS = 300L
    }

    private var playerState = State.STATE_DEFAULT
    private val mediaPlayer = MediaPlayer()
    private val handler = Handler(Looper.getMainLooper())

    // Если плеер открыт с сохраненной позицией не затирать ее
    // при выходе без запуска воспроизведения
    private var playbackHasStarted = false
    private val positionFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    private val positionUpdate = Runnable {
        updatePosition(mediaPlayer.currentPosition)
        postPositionUpdate()
    }

    private fun updatePosition(currentPosition: Int) {
        if (playbackHasStarted) {
            sharedPrefs.edit().putInt(track.trackId.toString(), currentPosition).apply()
        }
        positionFormat
            .format(currentPosition)
            .let { positionUpdateCallback(it) }
    }

    fun preparePlayer() {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = State.STATE_PREPARED
            sharedPrefs.getInt(track.trackId.toString(), 0)
                .let {
                    mediaPlayer.seekTo(it)
                    updatePosition(it)
                }
            stateUpdateCallback(playerState)
        }
        mediaPlayer.setOnCompletionListener {
            Log.d("TAG", "preparePlayer: player state Completed")
            playerState = State.STATE_PREPARED
            removePositionUpdates()
            updatePosition(0)
            stateUpdateCallback(playerState)
        }
    }

    private fun startPlayer() {
        playbackHasStarted = true
        mediaPlayer.start()
        postPositionUpdate()
        playerState = State.STATE_PLAYING
        stateUpdateCallback(playerState)
    }

    private fun postPositionUpdate() {
        handler.postDelayed(positionUpdate, POSITION_UPDATE_DELAY_MS)
    }

    fun pausePlayer() {
        mediaPlayer.pause()
        removePositionUpdates()
        playerState = State.STATE_PAUSED
        stateUpdateCallback(playerState)
    }

    private fun removePositionUpdates() {
        handler.removeCallbacks(positionUpdate)
    }

    fun playbackControl() {
        when (playerState) {
            State.STATE_PLAYING -> {
                pausePlayer()
            }

            State.STATE_PREPARED,
            State.STATE_PAUSED -> {
                startPlayer()
            }

            else -> {
                Log.d("TAG", "playbackControl: unsupported state $playerState")
            }
        }
    }

    fun release() {
        mediaPlayer.release()
    }

    enum class State {
        STATE_DEFAULT,
        STATE_PREPARED,
        STATE_PLAYING,
        STATE_PAUSED,
    }
}