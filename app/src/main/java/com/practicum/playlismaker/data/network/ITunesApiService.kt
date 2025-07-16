package com.practicum.playlismaker.data.network

import com.practicum.playlismaker.data.dto.ITunesResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApiService {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<ITunesResponseDto>
}