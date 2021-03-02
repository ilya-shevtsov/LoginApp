package com.example.loginapp.music.song.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongDto(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("duration")
    val duration: String

) : Serializable