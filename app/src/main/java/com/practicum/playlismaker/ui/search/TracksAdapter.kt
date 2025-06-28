package com.practicum.playlismaker.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlismaker.R
import com.practicum.playlismaker.domain.models.Track
import com.practicum.playlismaker.ui.clickDebounce

class TracksAdapter(
    private val itemClickListener: ((Track) -> Unit)? = null
) : RecyclerView.Adapter<TracksViewHolder>() {

    private val tracks: MutableList<Track> = mutableListOf()
    fun update(tracks: List<Track>) {
        this.tracks.clear()
        this.tracks.addAll(tracks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track_list, parent, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        val track = tracks[position]
        holder.bind(track)
        holder.itemView.setOnClickListener {
            if (clickDebounce()) itemClickListener?.invoke(track)
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}