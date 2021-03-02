package com.example.loginapp.music.albums.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumsPreviewResponse(

    @SerializedName("data")
    val data: List<AlbumsPreviewDto>

) : Serializable






