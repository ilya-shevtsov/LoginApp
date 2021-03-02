package com.example.loginapp.common

import android.app.Application
import androidx.room.Room
import com.example.loginapp.common.database.MyDataBase


class LoginAppApplication : Application() {

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
