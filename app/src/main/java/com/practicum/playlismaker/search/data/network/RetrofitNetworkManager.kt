package com.practicum.playlismaker.search.data.network

import android.util.Log
import com.practicum.playlismaker.search.data.NetworkManager
import com.practicum.playlismaker.search.data.dto.BaseResponse
import com.practicum.playlismaker.search.data.dto.ITunesResponseDto
import com.practicum.playlismaker.search.data.dto.TracksSearchRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.IOException

class RetrofitNetworkManager(
    private val iTunesApiService: ITunesApiService
) : NetworkManager {

    override fun doRequest(dto: Any): BaseResponse = when (dto) {
        is TracksSearchRequest -> {
            try {
                val resp = iTunesApiService.search(dto.search).execute()
                val body = resp.body() ?: BaseResponse()
                body.apply { resultCode = resp.code() }
            } catch (e: IOException) {
                Log.w("RetrofitNetworkManager", e)
                BaseResponse().apply { resultCode = -1 }
            }
        }

        else -> {
            BaseResponse().apply { resultCode = 400 }
        }
    }

}