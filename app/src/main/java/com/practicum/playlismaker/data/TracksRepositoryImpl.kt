package com.practicum.playlismaker.data

import com.practicum.playlismaker.base.data.NetworkManager
import com.practicum.playlismaker.data.dto.ITunesResponseDto
import com.practicum.playlismaker.data.dto.TracksSearchRequest
import com.practicum.playlismaker.search.domain.api.TracksRepository
import com.practicum.playlismaker.search.domain.models.Track
import com.practicum.playlismaker.util.Resource

class TracksRepositoryImpl(private val networkManager: NetworkManager) : TracksRepository {

    override fun searchTracks(search: String): Resource<List<Track>> {
        val response = networkManager.doRequest(TracksSearchRequest(search))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                (response as ITunesResponseDto).results
                    .map { it.getTrack() }
                    .let { Resource.Success(it) }
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}