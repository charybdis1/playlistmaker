package com.practicum.playlismaker.base.data

import com.practicum.playlismaker.base.data.dto.BaseResponse

interface NetworkManager {
    fun doRequest(dto: Any): BaseResponse
}