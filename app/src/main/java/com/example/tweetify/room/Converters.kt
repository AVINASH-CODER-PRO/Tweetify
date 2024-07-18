package com.example.tweetify.room

import androidx.room.TypeConverter
import androidx.room.TypeConverters

class Converters {
    @TypeConverter
    fun fromBookmarkedTweets(bookmarkedTweets: BookmarkedTweets): String {
        return bookmarkedTweets.tweet
    }

    @TypeConverter
    fun toBookmarkedTweets(tweet: String): BookmarkedTweets {
        return BookmarkedTweets(tweet = tweet)
    }
}