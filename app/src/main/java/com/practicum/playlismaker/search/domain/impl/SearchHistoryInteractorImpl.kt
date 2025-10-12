package com.practicum.playlismaker.search.domain.impl

import com.practicum.playlismaker.search.domain.api.SearchHistoryInteractor
import com.practicum.playlismaker.search.domain.api.SearchHistoryRepository
import com.practicum.playlismaker.search.domain.models.Track

class SearchHistoryInteractorImpl(
    private val repository: SearchHistoryRepository
) : SearchHistoryInteractor {
    override fun update(track: Track) {
        repository.update(track)
    }

    override fun getHistory(): MutableList<Track> {
        return repository.getHistory()
    }

    override fun clear() {
        repository.clear()
    }
}