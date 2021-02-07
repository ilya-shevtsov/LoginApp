package com.example.loginapp.model

import com.google.gson.annotations.SerializedName

data class SongResponse(

    @SerializedName("data")
    val data: List<SongDetails>? = null
)
