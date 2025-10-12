package com.practicum.playlismaker.search.presentation

import com.practicum.playlismaker.search.domain.models.Track

sealed interface SearchState {

    data object Loading : SearchState

    data class Content(
        val type: Type,
        val tracks: List<Track>,
    ) : SearchState {
        enum class Type { SEARCH, HISTORY }
    }

    data class Error(
        val message: String
    ) : SearchState

    data class Empty(
        val message: String
    ) : SearchState

}