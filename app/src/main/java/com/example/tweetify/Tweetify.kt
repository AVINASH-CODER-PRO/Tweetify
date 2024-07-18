package com.example.tweetify

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tweetify.room.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Tweetify: Application() {

    companion object{
        lateinit var database: AppDatabase
    }

    override fun onCreate(){
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
}