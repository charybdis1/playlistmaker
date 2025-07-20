package com.practicum.playlismaker.search.domain.api

import com.practicum.playlismaker.search.domain.models.Track

interface TracksRepository {
    fun searchTracks(search: String): List<Track>
}