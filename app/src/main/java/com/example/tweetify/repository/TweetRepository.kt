package com.example.tweetify.repository

import com.example.tweetify.api.TweetifyApi
import com.example.tweetify.models.TweetList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetRepository @Inject constructor(private val tweetifyApi: TweetifyApi) {


    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>>
        get() = _categories


    private val _tweets = MutableStateFlow<List<TweetList>>(emptyList())
    val tweets: StateFlow<List<TweetList>>
        get() = _tweets


    suspend fun getCategories() {
        val response = tweetifyApi.getCategories()
        if(response.isSuccessful && response.body() != null){
            _categories.emit(response.body()!!)
        }
    }

    suspend fun getTweets(category: String){
        val response = tweetifyApi.getTweets("tweets[?(@.category==\"$category\")]")
        if(response.isSuccessful && response.body() != null){
            _tweets.emit(response.body()!!)
        }
    }

}