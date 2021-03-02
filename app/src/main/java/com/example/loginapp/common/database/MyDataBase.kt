package com.example.loginapp.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.loginapp.music.album.domain.AlbumEntity
import com.example.loginapp.music.song.domain.SongEntity


@Database(entities = [AlbumEntity::class, SongEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract val musicDao: MusicDataAccessObject
}
