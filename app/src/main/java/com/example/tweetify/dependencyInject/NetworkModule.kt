package com.example.tweetify.dependencyInject

import com.example.tweetify.Tweetify
import com.example.tweetify.api.TweetifyApi
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun ProvidesRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl("https://api.jsonbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun ProvidesTweetifyApi(retrofit: Retrofit) : TweetifyApi {
        return retrofit.create(TweetifyApi::class.java)
    }

    @Singleton
    @Provides
    fun ProvidesFirebaseDatabase() : FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }


}