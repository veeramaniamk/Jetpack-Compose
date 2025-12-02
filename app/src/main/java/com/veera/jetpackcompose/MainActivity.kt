package com.veera.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

        }

    }

    @Preview(showBackground = true)
    @Composable
    fun ImagePreview() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.thanos_img),
                contentDescription = "Test Image",
                modifier = Modifier.wrapContentWidth()
//                    .height(50.dp)
            )
        }
    }

    @Composable
    fun FixedHeightImage(
        imageResId: Int,
        fixedHeight: Dp = 50.dp,
        modifier: Modifier = Modifier
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Image",
            contentScale = ContentScale.Crop, // or Fit/FillWidth
            modifier = modifier
                .wrapContentWidth()              // Take all available width (parent controls it)
                .height(fixedHeight)
                .clip(RoundedCornerShape(4.dp))
        )
    }

}