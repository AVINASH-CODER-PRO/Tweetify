package com.example.tweetify.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insert(bookmarkedTweets: BookmarkedTweets)

    @Query("DELETE FROM bookmarked_tweets WHERE tweet = :bookmarkedTweets")
    suspend fun delete(bookmarkedTweets: BookmarkedTweets)

    @Query("SELECT * FROM bookmarked_tweets")
    // Add a query to get all the bookmarked tweets
    fun getAllBookmarkedTweets(): LiveData<List<BookmarkedTweets>>

}