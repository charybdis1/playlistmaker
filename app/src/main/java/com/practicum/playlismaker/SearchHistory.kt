package com.practicum.playlismaker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private const val SEARCHHISTORYKEY = "SEARCHHISTORYKEY"

class SearchHistory(private val sharedPrefs: SharedPreferences) {

    private val gson = Gson()
    fun update(track: Track) {
        val history = getHistory()
        history.remove(track)
        history.add(0, track)
        val listLimit = if (history.size > 10) 9 else history.size
        val historyOutString = gson.toJson(history.subList(0, listLimit))
        sharedPrefs.edit().putString(SEARCHHISTORYKEY, historyOutString).apply()
    }

    fun getHistory(): MutableList<Track> {
        val historyString = sharedPrefs.getString(SEARCHHISTORYKEY, "")
        val itemType = object : TypeToken<List<Track>>() {}.type
        return gson.fromJson<List<Track>>(historyString, itemType).orEmpty().toMutableList()
    }

    fun clear() {
        sharedPrefs.edit().remove(SEARCHHISTORYKEY).apply()
    }
}