package com.example.loginapp.music.album.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.music.song.domain.Song
import com.example.loginapp.music.song.dto.SongDto

class SongsAdapter: RecyclerView.Adapter<SongsHolder>() {

    private var songsList: List<Song> = mutableListOf()
    private lateinit var view: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.list_item_song, parent, false)
        return SongsHolder(view)
    }

    override fun onBindViewHolder(holder: SongsHolder, position: Int) {
        val song = songsList[position]
        holder.bind(song)
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    fun addData(data: List<Song>, refresh: Boolean) {
        if (refresh) {
            songsList = emptyList()
        }
        songsList = songsList + data
        notifyDataSetChanged()
    }
}

