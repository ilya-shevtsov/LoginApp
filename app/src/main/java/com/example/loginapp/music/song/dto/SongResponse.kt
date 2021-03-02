package com.example.loginapp.music.song.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongResponse(

    @SerializedName("data")

    val data: SongDto

) : Serializable
