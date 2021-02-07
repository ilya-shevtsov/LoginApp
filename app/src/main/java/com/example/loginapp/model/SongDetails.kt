package com.example.loginapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongDetails(

    @SerializedName("id") val mid: String,
    @SerializedName("name") val mName: String,
    @SerializedName("duration") val mDuration: String
) : Serializable