package com.practicum.playlismaker

data class ITunesResponse(
    val resultCount: Int, val results: List<Track>
)
