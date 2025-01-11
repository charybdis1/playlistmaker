package com.practicum.playlismaker

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TracksAdapter(
    private val tracks: List<Track>
) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track_list, parent, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    class TracksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val trackName: TextView = itemView.findViewById(R.id.track_name)
        private val trackInfo: TextView = itemView.findViewById(R.id.track_info)
        private val trackCover: ImageView = itemView.findViewById(R.id.track_cover)

        fun bind(model: Track) {
            trackName.text = model.trackName
            trackInfo.text = model.artistName + " • " + model.trackTime

            Glide.with(trackCover)
                .load(model.artworkUrl100)
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(2f, trackCover.context)))
                .into(trackCover)
        }

        private fun dpToPx(dp: Float, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            ).toInt()
        }
    }
}