package com.example.loginapp.music.albums.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.music.albums.dto.AlbumsPreviewDto

class AlbumsPreviewAdapter(
    private val onItemClicked: (item: AlbumsPreviewDto) -> Unit
) : RecyclerView.Adapter<AlbumsPreviewHolder>() {

    private var albumsList: List<AlbumsPreviewDto> = mutableListOf()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsPreviewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.list_item_album, parent, false)
        return AlbumsPreviewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsPreviewHolder, position: Int) {
        val album: AlbumsPreviewDto = albumsList[position]
        holder.bind(album, onItemClicked)

    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    fun addData(data: List<AlbumsPreviewDto>, refresh: Boolean) {
        if (refresh) {
            albumsList = emptyList()
        }
        albumsList = albumsList + data
        notifyDataSetChanged()
    }
}

