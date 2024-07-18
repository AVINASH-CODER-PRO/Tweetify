package com.example.tweetify.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [BookmarkedTweets::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){

    companion object{
        const val DATABASE_NAME = "app_database"
    }
    abstract fun bookmarkDao() : BookmarkDao
}