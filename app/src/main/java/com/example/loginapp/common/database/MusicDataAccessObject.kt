package com.example.loginapp.common.database

import androidx.room.*
import com.example.loginapp.music.album.domain.AlbumEntity
import com.example.loginapp.music.song.domain.SongEntity


interface MusicDataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(albums: List<AlbumEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(songs: List<SongEntity>)

    @Query("SELECT * FROM album_table")
    fun getAlbums(): List<AlbumEntity>

    @Delete
    fun deleteAlbum(album: AlbumEntity)

    @Query("DELETE FROM album_table where id = :albumId")
    fun deleteAlbumById(albumId: Int)


}
