package com.example.loginapp.music.songs.dto

import com.example.loginapp.music.song.dto.SongDetailsDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SongsResponse(

    @SerializedName("data")

    val data: List<SongDetailsDto>

) : Serializable
