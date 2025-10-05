package com.practicum.playlismaker.search.domain.api

import com.practicum.playlismaker.search.domain.models.Track
import com.practicum.playlismaker.util.Resource

interface TracksRepository {
    fun searchTracks(search: String): Resource<List<Track>>
}