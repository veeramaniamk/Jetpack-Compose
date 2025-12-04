package com.veera.jetpackcompose.presentation.feature.home

import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veera.jetpackcompose.R

@Preview(showBackground = true)
@Composable
fun Login(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_bg),
            contentDescription = "Page Background",
            modifier = modifier.wrapContentWidth().wrapContentHeight(),
            contentScale = ContentScale.Crop
        )
    }
}