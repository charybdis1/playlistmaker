package com.practicum.playlismaker.search.domain.api

import com.practicum.playlismaker.search.domain.models.Track

interface SearchHistoryRepository {
    fun update(track: Track)
    fun getHistory(): MutableList<Track>
    fun clear()
}