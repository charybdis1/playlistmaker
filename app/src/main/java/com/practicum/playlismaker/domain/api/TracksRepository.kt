package com.practicum.playlismaker.domain.api

import com.practicum.playlismaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(search: String): List<Track>
}