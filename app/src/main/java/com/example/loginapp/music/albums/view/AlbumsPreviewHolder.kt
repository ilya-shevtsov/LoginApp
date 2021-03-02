package com.example.loginapp.music.albums.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.music.albums.dto.AlbumsPreviewDto
import kotlinx.android.extensions.LayoutContainer

class AlbumsPreviewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val title: TextView = containerView.findViewById(R.id.listItemAlbumTitleTextView)
    private val releaseDate: TextView =
        containerView.findViewById(R.id.listItemAlbumReleaseDateTextView)

    fun bind(
        item: AlbumsPreviewDto,
        onItemClicked: (item: AlbumsPreviewDto) -> Unit
    ) {
        title.text = item.name
        releaseDate.text = item.releaseDate
        containerView.setOnClickListener {
            onItemClicked.invoke(item)
        }
    }
}
