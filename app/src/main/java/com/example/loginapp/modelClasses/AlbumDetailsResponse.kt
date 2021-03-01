package com.example.loginapp.modelClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumDetailsResponse(

    @SerializedName("data")
    val data: AlbumDetails

) : Serializable






