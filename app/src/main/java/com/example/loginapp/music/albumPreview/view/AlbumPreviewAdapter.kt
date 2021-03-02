package com.example.loginapp.music.albumPreview.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.music.albumPreview.dto.AlbumPreviewDto

class AlbumPreviewAdapter(
    private val onItemClicked: (item: AlbumPreviewDto) -> Unit
) : RecyclerView.Adapter<AlbumPreviewHolder>() {

    private var albumsList: List<AlbumPreviewDto> = mutableListOf()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPreviewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.list_item_album, parent, false)
        return AlbumPreviewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumPreviewHolder, position: Int) {
        val album: AlbumPreviewDto = albumsList[position]
        holder.bind(album, onItemClicked)

    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    fun addData(data: List<AlbumPreviewDto>, refresh: Boolean) {
        if (refresh) {
            albumsList = emptyList()
        }
        albumsList = albumsList + data
        notifyDataSetChanged()
    }
}

