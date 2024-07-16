package com.example.tweetify.api

import com.example.tweetify.models.TweetList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TweetifyApi {

    @GET("/v3/b/669282bdad19ca34f8871d2a?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category: String) : Response<List<TweetList>>


    @GET("/v3/b/669282bdad19ca34f8871d2a?meta=false")
    @Headers("X-JSON-Path:tweets..category")
    suspend fun getCategories() : Response<List<String>>
}