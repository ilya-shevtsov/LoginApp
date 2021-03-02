package com.example.loginapp.music.albumPreview.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AlbumPreviewResponse(

    @SerializedName("data")
    val data: List<AlbumPreviewDto>

) : Serializable






