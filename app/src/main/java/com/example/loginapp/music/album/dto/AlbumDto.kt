package com.example.loginapp.music.album.dto

import com.example.loginapp.music.song.dto.SongDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumDto(

    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("name")
    val name: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("songs")
    val songs: List<SongDto>

) : Serializable








