package com.example.loginapp.common.api

import com.example.loginapp.music.album.dto.AlbumDetailsResponse
import com.example.loginapp.music.albums.dto.AlbumsPreviewResponse
import com.example.loginapp.music.song.dto.SongDetailsResponse
import com.example.loginapp.music.songs.dto.SongsResponse
import com.example.loginapp.user.dto.UserDto
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ServerApi {

    @GET("user/")
    fun enterUser(): Single<UserDto>

    @POST("registration")
    fun registration(@Body user: UserDto): Completable

    @GET("albums")
    fun getAlbumsPreview(): Single<AlbumsPreviewResponse>

    @GET("albums/{id}")
    fun getAlbumDetails(@Path("id") id: Int): Single<AlbumDetailsResponse>

    @GET("songs")
    fun getSongs(): Call<SongsResponse>

    @GET("songs/{id}")
    fun getSong(@Path("id") id: Int): Call<SongDetailsResponse>
}