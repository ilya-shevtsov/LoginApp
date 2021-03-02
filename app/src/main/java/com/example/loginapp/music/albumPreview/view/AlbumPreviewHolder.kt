package com.example.loginapp.music.albumPreview.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.music.albumPreview.dto.AlbumPreviewDto
import kotlinx.android.extensions.LayoutContainer

class AlbumPreviewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val title: TextView = containerView.findViewById(R.id.listItemAlbumTitleTextView)
    private val releaseDate: TextView =
        containerView.findViewById(R.id.listItemAlbumReleaseDateTextView)

    fun bind(
        item: AlbumPreviewDto,
        onItemClicked: (item: AlbumPreviewDto) -> Unit
    ) {
        title.text = item.name
        releaseDate.text = item.releaseDate
        containerView.setOnClickListener {
            onItemClicked.invoke(item)
        }
    }
}
