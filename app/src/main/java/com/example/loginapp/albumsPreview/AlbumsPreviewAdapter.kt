package com.example.loginapp.albumsPreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.modelClasses.AlbumsPreview

class AlbumsPreviewAdapter(
    private val onItemClicked: (item: AlbumsPreview) -> Unit
) : RecyclerView.Adapter<AlbumsPreviewHolder>() {

    private var albumsList: List<AlbumsPreview> = mutableListOf()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsPreviewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.list_item_album, parent, false)
        return AlbumsPreviewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsPreviewHolder, position: Int) {
        val album: AlbumsPreview = albumsList[position]
        holder.bind(album, onItemClicked)

    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    fun addData(data: List<AlbumsPreview>, refresh: Boolean) {
        if (refresh) {
            albumsList = emptyList()
        }
        albumsList = albumsList + data
        notifyDataSetChanged()
    }
}

