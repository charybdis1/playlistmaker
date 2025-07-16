package com.practicum.playlismaker.data

import com.practicum.playlismaker.data.dto.ITunesResponseDto
import com.practicum.playlismaker.data.dto.TracksSearchRequest
import com.practicum.playlismaker.domain.api.TracksRepository
import com.practicum.playlismaker.domain.models.Track
import com.practicum.playlismaker.ui.formatTime

class TracksRepositoryImpl(private val networkManager: NetworkManager) : TracksRepository {

    override fun searchTracks(search: String): List<Track> {
        val response = networkManager.doRequest(TracksSearchRequest(search))
        return if (response.resultCode == 200) {
            (response as ITunesResponseDto).results.map { it.getTrack() }
        } else {
            emptyList()
        }
    }
}