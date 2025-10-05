package com.practicum.playlismaker.search.domain.impl

import com.practicum.playlismaker.search.domain.api.TracksInteractor
import com.practicum.playlismaker.search.domain.api.TracksRepository
import com.practicum.playlismaker.util.Resource
import java.util.concurrent.Executors

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    private val executor = Executors.newCachedThreadPool()
    override fun searchTracks(
        search: String,
        consumer: TracksInteractor.TracksConsumer
    ) {
        executor.execute {
            when (val resource = repository.searchTracks(search)) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }
    }
}