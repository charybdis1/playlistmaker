package com.practicum.playlismaker.domain.models

data class Track(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String,
    val collectionName: String?,
    val releaseDate: String,
    val country: String,
    val primaryGenreName: String,
    val previewUrl: String
)