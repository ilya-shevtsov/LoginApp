package com.example.loginapp

import com.example.loginapp.modelClasses.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ServerApi {

    @GET("user/")
    fun enterUser(): Single<UserDtoResponse>

    @POST("registration")
    fun registration(@Body user: UserDto): Completable

    @GET("albums")
    fun getAlbumsPreview(): Single<AlbumsPreviewResponse>

    @GET("albums/{id}")
    fun getAlbumDetails(@Path("id") id: Int): Call<AlbumDetailsResponse>

    @GET("songs")
    fun getSongs(): Call<SongsResponse>

    @GET("songs/{id}")
    fun getSong(@Path("id") id: Int): Call<SongResponse>
}