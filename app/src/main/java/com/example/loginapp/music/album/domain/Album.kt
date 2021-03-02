package com.example.loginapp.music.album.domain

import com.example.loginapp.music.song.domain.Song

data class Album(
    val id: Int,
    val name: String,
    val releaseDate: String,
    val songs: List<Song>
)

