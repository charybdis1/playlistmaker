package com.practicum.playlismaker.search.data.dto

import com.practicum.playlismaker.search.data.dto.BaseResponse

data class ITunesResponseDto(
    val resultCount: Int, val results: List<TrackDto>
): BaseResponse()
