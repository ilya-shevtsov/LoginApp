package com.example.loginapp.model

import com.google.gson.annotations.SerializedName


data class AlbumDetails(

    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("songs")
    val songs: List<SongDetails>? = null
)








