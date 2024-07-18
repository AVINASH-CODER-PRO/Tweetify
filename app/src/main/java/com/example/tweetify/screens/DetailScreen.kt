package com.example.tweetify.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tweetify.GradientBackgroundBrush
import com.example.tweetify.gradcolors
import com.example.tweetify.room.BookmarkedTweets
import com.example.tweetify.viewmodles.BookmarkViewModel
import com.example.tweetify.viewmodles.DetailViewModel

@Composable
fun DetailScreen() {

//    var bookmarkedTweets by remember { mutableStateOf(setOf<BookmarkedTweets>()) }
    val bookmarkViewModel : BookmarkViewModel = hiltViewModel()
    val bookmarkedTweets by viewModel<BookmarkViewModel>().bookmarkedTweets.observeAsState()

    val detailViewModel : DetailViewModel = hiltViewModel()
    val tweets by detailViewModel.tweets.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                GradientBackgroundBrush(true, gradcolors)
            ),
        content = {
            if(tweets.isNotEmpty()) {
                item {
                    Box() {
                        Text(
                            text = tweets[0].category,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(
                                    top = 18.dp,
                                    end = 8.dp,
                                    start = 12.dp,
                                    bottom = 8.dp
                                )
                                .align(Alignment.CenterStart),
                            fontWeight = FontWeight.SemiBold,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
                            color = Color.White
                        )
                        HorizontalDivider(thickness = 1.2.dp, color = Color(0xFFE0E0E0))
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(36.dp)) // Adjust the height as needed
            }

            items(tweets) { tweet ->
                bookmarkedTweets?.let {
                    TweetListItem(tweet.text,
                        isBookmarked = it.any{ it.tweet == tweet.text },
                        onBookmarkedClick = {
                            val bookmarkedTweet = BookmarkedTweets(tweet = tweet.text)
                            if(bookmarkedTweets!!.any{ it.tweet == tweet.text }) {
                                println("DetailScreen - Removing bookmark: ${tweet.text}")
                                bookmarkViewModel.removeBookmark(bookmarkedTweet)
                            } else {
                                println("DetailScreen - Adding bookmark: ${tweet.text}")
                                bookmarkViewModel.addBookmark(bookmarkedTweet)
                            }
                        }
                    )
                }
        }
    }

    )
}


@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel = hiltViewModel()) {

    val bookmarkedTweets by bookmarkViewModel.bookmarkedTweets.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GradientBackgroundBrush(true, gradcolors))
    ) {
        Text(
            text = "Bookmarked Tweets",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        if (bookmarkedTweets?.isEmpty() == true) {
            Text(
                text = "No bookmarked tweets",
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        } else {
            bookmarkedTweets?.let {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(bookmarkedTweets!!) { bookmarkedTweet ->
                        BookmarkListItem(tweet = bookmarkedTweet.tweet) {
                            bookmarkViewModel.removeBookmark(bookmarkedTweet)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BookmarkListItem(tweet: String, onRemoveClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        border = BorderStroke(0.5.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF655b81))
    ) {
            Text(
                text = tweet,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)

            )
            IconButton(onClick = { onRemoveClick(tweet) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from bookmarks"
                )
            }
    }
}

@Composable
fun TweetListItem(tweet : String, isBookmarked : Boolean, onBookmarkedClick : () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        border = BorderStroke(0.5.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF655b81)),
        content = {
            Text(
                text = tweet,
                modifier = Modifier
                    .padding(8.dp),
                fontWeight = FontWeight.SemiBold,
            )
            IconButton(onClick = onBookmarkedClick) {
                Icon(
                    imageVector = if (isBookmarked) Icons.Default.Check else Icons.Default.Add,
                    contentDescription = if (isBookmarked) "Remove from bookmarks" else "Add to bookmarks"
                )
            }
        }

    )
}