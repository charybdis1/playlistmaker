package com.practicum.playlismaker

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NetworkManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val iTunesApiService: ITunesApiService = retrofit.create()
    fun getTracks(
        search: String,
        action: (ITunesResponse) -> Unit,
        errorAction: (ErrorType) -> Unit
    ) {
        val tag = "network"
        Log.d(tag,"getTracks $search")
        iTunesApiService.search(search).enqueue(object : Callback<ITunesResponse> {
            override fun onResponse(
                call: Call<ITunesResponse>,
                response: Response<ITunesResponse>
            ) {
                Log.d(tag,"onResponse")
                val body = response.body()
                if (response.isSuccessful && body != null && body.resultCount > 0) {
                    Log.d(tag,"onResponse ok")
                    action(body)
                } else if (body != null && body.resultCount == 0) {
                    Log.d(tag,"onResponse empty")
                    errorAction(ErrorType.EMPTY_RESPONCE)
                } else if (body == null) {
                    errorAction(ErrorType.RESPONCE_ERROR)
                } else {
                    val errorJson = response.errorBody()?.string().orEmpty()
                    Log.d(tag, errorJson)
                    errorAction(ErrorType.RESPONCE_ERROR)
                }
            }

            override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                Log.d(tag,"onFailure")
                t.printStackTrace()
                errorAction(ErrorType.RESPONCE_ERROR)
            }
        })
    }

    enum class ErrorType {
        EMPTY_RESPONCE, RESPONCE_ERROR
    }
}