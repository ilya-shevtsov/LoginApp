package com.example.loginapp

import com.example.loginapp.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ServerApi {

    @POST("registration")
    fun registration(@Body user: UserDto): Call<Void>

    @GET("albums")
    fun getAlbumsPreview(): Call<AlbumsPreviewResponse>

    @GET("albums/{id}")
    fun getAlbumDetails(@Path("id") id: Int): Call<AlbumDetailsResponse>

    @GET("songs")
    fun getSongs(): Call<SongsResponse>

    @GET("songs/{id}")
    fun getSong(@Path("id") id: Int): Call<SongResponse>
}