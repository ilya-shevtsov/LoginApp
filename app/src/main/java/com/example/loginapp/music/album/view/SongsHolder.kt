package com.example.loginapp.music.album.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.music.song.dto.SongDto
import kotlinx.android.extensions.LayoutContainer

class SongsHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val title: TextView = containerView.findViewById(R.id.listItemSongTitleTextView)
    private val duration: TextView =
        containerView.findViewById(R.id.listItemSongDurationTextView)

    fun bind(item: SongDto) {
        title.text = item.name
        duration.text = item.duration

    }
}