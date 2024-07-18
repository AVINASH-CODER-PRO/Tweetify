package com.example.tweetify.dependencyInject

import android.content.Context
import androidx.room.Room
import com.example.tweetify.room.AppDatabase
import com.example.tweetify.room.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context : Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(appDatabase: AppDatabase): BookmarkDao {
        return appDatabase.bookmarkDao()
    }

}