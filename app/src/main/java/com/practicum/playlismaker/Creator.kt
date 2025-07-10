package com.practicum.playlismaker

import com.practicum.playlismaker.data.TracksRepositoryImpl
import com.practicum.playlismaker.data.network.RetrofitNetworkManager
import com.practicum.playlismaker.domain.api.TracksInteractor
import com.practicum.playlismaker.domain.api.TracksRepository
import com.practicum.playlismaker.domain.impl.TracksInteractorImpl

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkManager())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }
}