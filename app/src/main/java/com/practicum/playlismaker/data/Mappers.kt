package com.practicum.playlismaker.data

import com.practicum.playlismaker.data.dto.TrackDto
import com.practicum.playlismaker.domain.models.Track
import com.practicum.playlismaker.ui.formatTime

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
