package com.example.loginapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumsPreview(

    @SerializedName("id")
    val id:Int = 0,

    @SerializedName("name")
    val name: String,

    @SerializedName("songs_count")
    val songCount: Int,

    @SerializedName("release_date")
    val releaseDate: String

) : Serializable