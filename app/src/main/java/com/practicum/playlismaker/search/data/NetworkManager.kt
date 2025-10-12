package com.practicum.playlismaker.search.data

import com.practicum.playlismaker.search.data.dto.BaseResponse

interface NetworkManager {
    fun doRequest(dto: Any): BaseResponse
}