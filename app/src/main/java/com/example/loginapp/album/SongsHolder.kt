package com.example.loginapp.album

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.model.SongDetails
import kotlinx.android.extensions.LayoutContainer

class SongsHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val title: TextView = containerView.findViewById(R.id.listItemSongTitleTextView)
    private val duration: TextView =
        containerView.findViewById(R.id.listItemSongDurationTextView)


    fun bind(item: SongDetails) {
        title.text = item.name
        duration.text = item.duration

    }
}

//    fun bind(item: SongDetails,onItemClicked:(item:SongDetails)->Unit) {
//        title.text = item.name
//        duration.text = item.duration
//        containerView.setOnClickListener {
//            onItemClicked.invoke(item)
//        }
//    }