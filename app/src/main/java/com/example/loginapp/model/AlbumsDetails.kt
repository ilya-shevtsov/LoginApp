package com.example.loginapp.model

import com.google.gson.annotations.SerializedName

data class AlbumsDetails(

    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("songs_count")
    val mSongCount: Int = 0,

    @SerializedName("release_date")
    val releaseDate: String? = null,
)