package com.example.tweetify.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.tweetify.viewmodles.DetailViewModel

@Composable
fun DetailScreen() {
    val detailViewModel : DetailViewModel = hiltViewModel()
    val tweets = detailViewModel.tweets.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                GradientBackgroundBrush(true, gradcolors)),
        content = {
            if(tweets.value.isNotEmpty()) {
                item {
                    Box() {
                        Text(
                            text = tweets.value[0].category,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(
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

            items(tweets.value) { tweet ->
                TweetListItem(tweet.text)
        }
    })
}

@Composable
fun TweetListItem(tweet : String) {
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
        }

    )
}