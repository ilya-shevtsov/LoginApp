package com.example.loginapp.albumsPreview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.modelClasses.AlbumsPreview
import kotlinx.android.extensions.LayoutContainer

class AlbumsPreviewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    private val title: TextView = containerView.findViewById(R.id.listItemAlbumTitleTextView)
    private val releaseDate: TextView =
        containerView.findViewById(R.id.listItemAlbumReleaseDateTextView)

    fun bind(
        item: AlbumsPreview,
        onItemClicked: (item: AlbumsPreview) -> Unit
    ) {
        title.text = item.name
        releaseDate.text = item.releaseDate
        containerView.setOnClickListener {
            onItemClicked.invoke(item)
        }
    }
}