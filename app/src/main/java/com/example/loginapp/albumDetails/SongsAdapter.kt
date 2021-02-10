package com.example.loginapp.albumDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import com.example.loginapp.modelClasses.SongDetails

class SongsAdapter: RecyclerView.Adapter<SongsHolder>() {

//class SongsAdapter(private val onItemClicked: (item: SongDetails) -> Unit) :
//    RecyclerView.Adapter<SongsHolder>() {

    private var songsList: List<SongDetails> = mutableListOf()
    private lateinit var view: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.list_item_song, parent, false)
        return SongsHolder(view)
    }

    override fun onBindViewHolder(holder: SongsHolder, position: Int) {
        val song: SongDetails = songsList[position]
        holder.bind(song)
//        holder.bind(song, onItemClicked)

    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    fun addData(data: List<SongDetails>, refresh: Boolean) {
        if (refresh) {
            songsList = emptyList()
        }
        songsList = songsList + data
        notifyDataSetChanged()
    }
}

