package com.example.loginapp.modelClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongResponse(

    @SerializedName("data")

    val data: SongDetails

) : Serializable
