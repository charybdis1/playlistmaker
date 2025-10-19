package com.practicum.playlismaker.search.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.practicum.playlismaker.search.data.NetworkManager
import com.practicum.playlismaker.search.data.SearchHistoryRepositoryImpl
import com.practicum.playlismaker.search.data.network.ITunesApiService
import com.practicum.playlismaker.search.data.network.RetrofitNetworkManager
import com.practicum.playlismaker.search.domain.api.SearchHistoryRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<ITunesApiService> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences("practicum_example_preferences", AppCompatActivity.MODE_PRIVATE)
    }

    factory { Gson() }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    single<NetworkManager> {
        RetrofitNetworkManager(get())
    }

}