package com.example.loginapp.modelClasses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AlbumsPreviewResponse(

    @SerializedName("data")
    val data: List<AlbumsPreview>

) : Serializable





