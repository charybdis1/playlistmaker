package com.practicum.playlismaker.search.domain.api

import com.practicum.playlismaker.search.domain.models.Track

interface TracksInteractor {
    fun searchTracks(search: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume(tracks: List<Track>?, errorMessage: String?)
    }
}