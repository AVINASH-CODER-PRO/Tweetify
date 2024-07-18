package com.example.tweetify.viewmodles

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tweetify.Tweetify
import com.example.tweetify.room.BookmarkDao
import com.example.tweetify.room.BookmarkedTweets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor() : ViewModel() {

    private val bookmarkDao = Tweetify.database.bookmarkDao()
    val bookmarkedTweets: LiveData<List<BookmarkedTweets>> = bookmarkDao.getAllBookmarkedTweets()


    fun addBookmark(bookmarkedTweets: BookmarkedTweets){
        viewModelScope.launch {
            bookmarkDao.insert(bookmarkedTweets)
            }
        }

    fun removeBookmark(bookmarkedTweets: BookmarkedTweets){
        viewModelScope.launch {
            bookmarkDao.delete(bookmarkedTweets)
            }

        }
}