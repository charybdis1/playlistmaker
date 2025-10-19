package com.practicum.playlismaker.search.di

import com.practicum.playlismaker.search.data.SearchHistoryRepositoryImpl
import com.practicum.playlismaker.search.data.TracksRepositoryImpl
import com.practicum.playlismaker.search.domain.api.SearchHistoryRepository
import com.practicum.playlismaker.search.domain.api.TracksRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<TracksRepository> {
        TracksRepositoryImpl(get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }
}