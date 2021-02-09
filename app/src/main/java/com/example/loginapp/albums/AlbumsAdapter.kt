package com.example.loginapp.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.model.AlbumsPreview


class AlbumsAdapter : RecyclerView.Adapter<AlbumsHolder>() {

    private var albumsList: List<AlbumsPreview> = mutableListOf()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.list_item_album, parent, false)
        return AlbumsHolder(view)
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        holder.bind(albumsList[position])
    }

    fun addData(data: List<AlbumsPreview>, refresh: Boolean) {
        if (refresh) {
            albumsList = emptyList()
        }
        albumsList = albumsList + data
        notifyDataSetChanged()
    }

}

