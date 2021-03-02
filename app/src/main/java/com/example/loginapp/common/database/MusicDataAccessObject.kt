package com.example.loginapp.common.database

import androidx.room.*
import com.example.loginapp.music.album.database.AlbumEntity
import com.example.loginapp.music.song.database.SongEntity


interface MusicDataAccessObject {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(album: AlbumEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSongs(songs: List<SongEntity>)

    @Query("SELECT * FROM album_table")
    fun getAlbums(): List<AlbumEntity>

    @Query("SELECT * FROM album_table WHERE id = :id")
    fun getAlbumById(id: Int): AlbumEntity?

    @Delete
    fun deleteAlbum(album: AlbumEntity)

    @Query("DELETE FROM album_table where id = :albumId")
    fun deleteAlbumById(albumId: Int)

    @Query("SELECT * FROM song_table WHERE albumId = :AlbumId")
    fun getSongsForAlbum(AlbumId: Int): List<SongEntity>?

}
