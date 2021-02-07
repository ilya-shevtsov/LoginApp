package com.example.loginapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AlbumDetails(

    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("name")
    val name: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("songs")
    val songs: List<SongDetails>

) : Serializable








