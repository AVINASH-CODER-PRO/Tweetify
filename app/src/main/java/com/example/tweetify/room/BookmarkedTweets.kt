package com.example.tweetify.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_tweets")
data class BookmarkedTweets(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tweet: String
)
