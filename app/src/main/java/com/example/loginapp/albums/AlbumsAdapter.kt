package com.example.loginapp.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.model.AlbumsPreview


class AlbumsAdapter(
    private val onItemClicked: (item: AlbumsPreview) -> Unit
) : RecyclerView.Adapter<AlbumsHolder>() {

    private var albumsList: List<AlbumsPreview> = mutableListOf()
    private lateinit var view: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.list_item_album, parent, false)
        return AlbumsHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
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

