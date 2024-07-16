package com.example.tweetify

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val gradcolors = listOf(
    Color(0xFFA1BE95),
    Color(0xFFA1BE95),
)


@Composable
fun GradientBackgroundBrush(isVerticalGradient:Boolean,color: List<Color>):Brush {

    val endOffset = if(isVerticalGradient){
        Offset(0f, Float.POSITIVE_INFINITY)
    }
    else{
        Offset(Float.POSITIVE_INFINITY, 0f)
    }

    return Brush.linearGradient(
        colors = color,
        start = Offset.Zero,
        end = endOffset
    )
}