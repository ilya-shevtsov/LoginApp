package com.example.loginapp.music.song.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongDetailsResponse(

    @SerializedName("data")

    val data: SongDetailsDto

) : Serializable
