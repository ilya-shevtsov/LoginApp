package com.example.loginapp.music.album.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumDetailsResponse(

    @SerializedName("data")
    val data: AlbumDetailsDto

) : Serializable






