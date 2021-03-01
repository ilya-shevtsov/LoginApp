package com.example.loginapp

import android.app.Application
import androidx.room.Room
import com.example.loginapp.dataBase.MyDataBase


class App : Application() {

    val dataBase: MyDataBase by lazy { createDatabase() }

    private fun createDatabase(): MyDataBase = Room
        .databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "music_database"
        )
        .fallbackToDestructiveMigration()
        .build()
}
