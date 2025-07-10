package com.practicum.playlismaker.data.dto

import com.practicum.playlismaker.domain.models.Track

data class ITunesResponseDto(
    val resultCount: Int, val results: List<TrackDto>
): BaseResponse()
