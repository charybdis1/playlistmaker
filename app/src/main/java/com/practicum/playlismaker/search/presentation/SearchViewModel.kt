package com.practicum.playlismaker.search.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlismaker.creator.Creator
import com.practicum.playlismaker.search.domain.api.TracksInteractor
import com.practicum.playlismaker.search.domain.models.Track
import com.practicum.playlismaker.search.presentation.SearchState.Content.Type
import com.practicum.playlismaker.util.getPrefs

class SearchViewModel(private val context: Context) : ViewModel() {

    private val searchHistory = Creator.provideSearchHistoryInteractor(context.getPrefs())
    private val interactor = Creator.provideTracksInteractor()
    private val stateLiveData = MutableLiveData<SearchState>()
    fun observeState(): LiveData<SearchState> = stateLiveData

    fun getHistory() {
        stateLiveData.postValue(SearchState.Content(Type.HISTORY, searchHistory.getHistory()))
    }

    fun clearHistory() {
        searchHistory.clear()
        getHistory()
    }

    fun search(search: String) {
        stateLiveData.postValue(SearchState.Loading)
        interactor.searchTracks(search, object : TracksInteractor.TracksConsumer {
            override fun consume(
                tracks: List<Track>?,
                errorMessage: String?
            ) {
                when {
                    !tracks.isNullOrEmpty() -> SearchState.Content(Type.SEARCH, tracks)
                    tracks?.isEmpty() == true -> SearchState.Empty("")
                    else -> SearchState.Error(errorMessage ?: "Unknown error")
                }.let(stateLiveData::postValue)
            }
        })
    }

    fun updateHistory(track: Track) {
        searchHistory.update(track)
    }
}