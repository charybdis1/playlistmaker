package com.practicum.playlismaker.domain.api

import com.practicum.playlismaker.domain.models.Track

interface TracksInteractor {
    fun searchTracks(search: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(tracks: List<Track>)
    }
}