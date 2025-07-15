package com.practicum.playlismaker.domain.api

import com.practicum.playlismaker.domain.models.Track

interface SearchHistoryRepository {
    fun update(track: Track)
    fun getHistory(): MutableList<Track>
    fun clear()
}