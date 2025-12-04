package com.veera.jetpackcompose.presentation.feature.home

import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veera.jetpackcompose.R

@Preview(showBackground = true)
@Composable
fun Login(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.ic_login_bg),
            contentDescription = "Page Background",
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // White panel covering bottom half with top rounded corners
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // half of the screen height
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            color = MaterialTheme.colorScheme.surface // usually white in light theme
        ) {
            // Content inside the white panel – scrollable if needed
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Example content
                Text(
                    text = "Title",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )

                Text(
                    text = "Subtitle or description goes here. Put any content you want: lists, images, forms, etc.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Example rows / items
                for (i in 1..6) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text("List item #$i", modifier = Modifier.padding(start = 12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = { /* handle click */ }, modifier = Modifier.fillMaxWidth()) {
                    Text("Action")
                }
            }
        }
    }
}