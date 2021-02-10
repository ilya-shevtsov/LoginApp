package com.example.loginapp.modelClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongsResponse(

    @SerializedName("data")

    val data: List<SongDetails>

) : Serializable
