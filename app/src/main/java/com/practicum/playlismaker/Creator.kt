package com.practicum.playlismaker

import android.content.SharedPreferences
import com.practicum.playlismaker.data.SearchHistoryRepositoryImpl
import com.practicum.playlismaker.data.TracksRepositoryImpl
import com.practicum.playlismaker.data.network.RetrofitNetworkManager
import com.practicum.playlismaker.domain.api.SearchHistoryInteractor
import com.practicum.playlismaker.domain.api.SearchHistoryRepository
import com.practicum.playlismaker.domain.api.TracksInteractor
import com.practicum.playlismaker.domain.api.TracksRepository
import com.practicum.playlismaker.domain.impl.SearchHistoryInteractorImpl
import com.practicum.playlismaker.domain.impl.TracksInteractorImpl

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkManager())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }

    private fun getSearchHistoryRepository(
        preferences: SharedPreferences
    ): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(preferences)
    }

    fun provideSearchHistoryInteractor(
        preferences: SharedPreferences
    ): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(getSearchHistoryRepository(preferences))
    }
}