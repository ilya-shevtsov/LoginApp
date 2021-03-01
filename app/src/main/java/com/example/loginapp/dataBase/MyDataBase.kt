package com.example.loginapp.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [AlbumEntity::class, SongEntity::class], version = 1)
abstract class MyDataBase : RoomDatabase() {
    abstract val musicDao: MusicDataAccessObject
}