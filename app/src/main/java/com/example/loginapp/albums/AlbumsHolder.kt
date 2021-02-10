package com.example.loginapp.albums

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.model.AlbumsPreview
import kotlinx.android.extensions.LayoutContainer

class AlbumsHolder(
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
