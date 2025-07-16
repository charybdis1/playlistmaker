package com.practicum.playlismaker.domain.impl

import com.practicum.playlismaker.domain.api.TracksInteractor
import com.practicum.playlismaker.domain.api.TracksRepository
import java.util.concurrent.Executors

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()
    override fun searchTracks(search: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            consumer.consume(repository.searchTracks(search))
        }
    }
}