package com.practicum.playlismaker.search.data

import com.practicum.playlismaker.search.data.dto.TrackDto
import com.practicum.playlismaker.search.domain.models.Track
import com.practicum.playlismaker.util.formatTime

fun TrackDto.getTrack() = Track(
    trackId,
    trackName,
    artistName,
    formatTime(),
    artworkUrl100,
    collectionName,
    releaseDate,
    country,
    primaryGenreName,
    previewUrl
)
