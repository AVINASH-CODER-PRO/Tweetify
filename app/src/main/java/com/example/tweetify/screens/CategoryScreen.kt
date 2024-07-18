package com.example.tweetify.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.tweetify.GradientBackgroundBrush
import com.example.tweetify.R
import com.example.tweetify.gradcolors
import com.example.tweetify.viewmodles.CategoryViewModel
import com.example.tweetify.viewmodles.ImageViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue



@Composable
fun CategoryScreen(onClick: (category: String) -> Unit) {

    val categoryViewModel : CategoryViewModel = hiltViewModel()
    val categories: State<List<String>> = categoryViewModel.categories.collectAsState()
    val imageViewModel : ImageViewModel = hiltViewModel()

    //Fetch all the value at once when the composable is first displayed
    val fetchedImages by imageViewModel.imageUrls.collectAsState()
    imageViewModel.fetchAllImages(categories.value.distinct())



    Column(modifier = Modifier
        .fillMaxSize()
        .background(GradientBackgroundBrush(true, gradcolors))
    )
    {
        Text(
            text = "Category",
            fontSize = 30.sp,
            modifier = Modifier.padding(top=18.dp, end = 8.dp, start = 12.dp, bottom = 8.dp)
                .align(Alignment.Start),
            fontWeight = FontWeight.SemiBold,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
            color = Color(0xFFFFFFFF)
        )

        Divider(color = Color(0xFF000000), thickness = 0.2.dp)
        Spacer(modifier = Modifier.height(10.dp))
        if (categories.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                items(categories.value.distinct()) { category ->
                    val imageUrl = fetchedImages[category]
                    CategoryItem(category = category, imageUrl = imageUrl, onClick)
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: String, imageUrl: String?,onClick: (category : String) -> Unit) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onClick(category)
            }
            .size(height = 190.dp,width = 125.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF98866)),
        contentAlignment = Alignment.BottomCenter,
    ){
        imageUrl?.let{ url ->
            Image(
                painter = rememberImagePainter(url, builder = {
                    placeholder(R.drawable.placeholder)
                    crossfade(1000)
                }),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(115.dp, 115.dp)
            )

        }
        Text(
            text = category,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(0.dp,10.dp)
                .align(Alignment.BottomCenter),
            color = Color(0xFFFFFFFF),
            fontWeight = FontWeight.SemiBold,


        )
    }
}