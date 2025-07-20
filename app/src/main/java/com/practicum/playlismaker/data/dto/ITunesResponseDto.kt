package com.practicum.playlismaker.data.dto

import com.practicum.playlismaker.base.data.dto.BaseResponse

data class ITunesResponseDto(
    val resultCount: Int, val results: List<TrackDto>
): BaseResponse()
