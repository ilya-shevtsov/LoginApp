package com.example.loginapp.modelClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongDetails(

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("duration")
    val duration: String

) : Serializable