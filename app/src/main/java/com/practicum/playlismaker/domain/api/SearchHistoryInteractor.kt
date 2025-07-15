package com.practicum.playlismaker.domain.api

import com.practicum.playlismaker.domain.models.Track

interface SearchHistoryInteractor {

    fun update(track: Track)
    fun getHistory(): MutableList<Track>
    fun clear()
}