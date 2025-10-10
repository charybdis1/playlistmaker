package com.practicum.playlismaker.creator

import android.content.SharedPreferences
import com.practicum.playlismaker.search.data.SearchHistoryRepositoryImpl
import com.practicum.playlismaker.search.data.TracksRepositoryImpl
import com.practicum.playlismaker.search.data.network.RetrofitNetworkManager
import com.practicum.playlismaker.search.domain.api.SearchHistoryInteractor
import com.practicum.playlismaker.search.domain.api.SearchHistoryRepository
import com.practicum.playlismaker.search.domain.api.TracksInteractor
import com.practicum.playlismaker.search.domain.api.TracksRepository
import com.practicum.playlismaker.search.domain.impl.SearchHistoryInteractorImpl
import com.practicum.playlismaker.search.domain.impl.TracksInteractorImpl

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