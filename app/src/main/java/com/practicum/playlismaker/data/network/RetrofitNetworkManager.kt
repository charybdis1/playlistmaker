package com.practicum.playlismaker.data.network

import android.util.Log
import com.practicum.playlismaker.data.NetworkManager
import com.practicum.playlismaker.data.dto.BaseResponse
import com.practicum.playlismaker.data.dto.ITunesResponseDto
import com.practicum.playlismaker.data.dto.TracksSearchRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitNetworkManager : NetworkManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesApiService: ITunesApiService = retrofit.create()

    override fun doRequest(dto: Any): BaseResponse = when (dto) {
        is TracksSearchRequest -> {
            val resp = iTunesApiService.search(dto.search).execute()
            val body = resp.body() ?: BaseResponse()
            body.apply { resultCode = resp.code() }
        }

        else -> {
            BaseResponse().apply { resultCode = 400 }
        }
    }

    fun getTracks(
        search: String,
        action: (ITunesResponseDto) -> Unit,
        errorAction: (ErrorType) -> Unit
    ) {
        val tag = "network"
        Log.d(tag, "getTracks $search")
        iTunesApiService.search(search).enqueue(object : Callback<ITunesResponseDto> {
            override fun onResponse(
                call: Call<ITunesResponseDto>,
                response: Response<ITunesResponseDto>
            ) {
                Log.d(tag, "onResponse")
                val body = response.body()
                if (response.isSuccessful && body != null && body.resultCount > 0) {
                    Log.d(tag, "onResponse ok")
                    action(body)
                } else if (body != null && body.resultCount == 0) {
                    Log.d(tag, "onResponse empty")
                    errorAction(ErrorType.EMPTY_RESPONCE)
                } else if (body == null) {
                    errorAction(ErrorType.RESPONCE_ERROR)
                } else {
                    val errorJson = response.errorBody()?.string().orEmpty()
                    Log.d(tag, errorJson)
                    errorAction(ErrorType.RESPONCE_ERROR)
                }
            }

            override fun onFailure(call: Call<ITunesResponseDto>, t: Throwable) {
                Log.d(tag, "onFailure")
                t.printStackTrace()
                errorAction(ErrorType.RESPONCE_ERROR)
            }
        })
    }

    enum class ErrorType {
        EMPTY_RESPONCE, RESPONCE_ERROR
    }
}