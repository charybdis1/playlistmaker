package com.practicum.playlismaker

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.Locale

class TracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val trackInfo: TextView = itemView.findViewById(R.id.track_info)
    private val trackCover: ImageView = itemView.findViewById(R.id.track_cover)

    fun bind(model: Track) {
        trackName.text = model.trackName
        val time = formatTime(model)
        trackInfo.text = "${model.artistName} • $time"

        Glide.with(trackCover)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(2f, trackCover.context)))
            .into(trackCover)
    }
}