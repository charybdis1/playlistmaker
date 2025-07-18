package com.practicum.playlismaker.data.dto

data class TrackDto(
    val trackId: Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val collectionName: String?,
    val releaseDate: String,
    val country: String,
    val primaryGenreName: String,
    val previewUrl: String
): BaseResponse()