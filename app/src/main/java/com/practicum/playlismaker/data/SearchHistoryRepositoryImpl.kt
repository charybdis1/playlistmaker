package com.practicum.playlismaker.data

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practicum.playlismaker.domain.api.SearchHistoryRepository
import com.practicum.playlismaker.domain.models.Track

private const val SEARCHHISTORYKEY = "SEARCHHISTORYKEY"

class SearchHistoryRepositoryImpl(private val sharedPrefs: SharedPreferences) :
    SearchHistoryRepository {

    private val gson = Gson()
    override fun update(track: Track) {
        val history = getHistory()
        history.remove(track)
        history.add(0, track)
        val listLimit = if (history.size > 10) 9 else history.size
        val historyOutString = gson.toJson(history.subList(0, listLimit))
        sharedPrefs.edit().putString(SEARCHHISTORYKEY, historyOutString).apply()
    }

    override fun getHistory(): MutableList<Track> {
        val historyString = sharedPrefs.getString(SEARCHHISTORYKEY, "")
        val itemType = object : TypeToken<List<Track>>() {}.type
        return gson.fromJson<List<Track>>(historyString, itemType).orEmpty().toMutableList()
    }

    override fun clear() {
        sharedPrefs.edit().remove(SEARCHHISTORYKEY).apply()
    }
}