package com.practicum.playlismaker.data

import com.practicum.playlismaker.data.dto.BaseResponse

interface NetworkManager {
    fun doRequest(dto: Any): BaseResponse
}