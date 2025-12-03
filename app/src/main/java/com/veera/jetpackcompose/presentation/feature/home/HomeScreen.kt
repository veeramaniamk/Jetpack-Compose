package com.veera.jetpackcompose.presentation.feature.home

import com.veera.jetpackcompose.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            verticalAlignment  = Alignment.CenterVertically,
            modifier = modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Image(
                contentDescription = "App Icon",
                painter = painterResource(id = R.drawable.thanos_img),
                modifier = Modifier.size(25.dp).clip(
                   CircleShape
                ),
            )
            Text(
                text = "SIMATS 360",
                textAlign = TextAlign.Center,
                modifier = Modifier.wrapContentSize()
                    .padding(
                        8.dp,
                        0.dp,
                        0.dp,
                        0.dp
                    )
            )
        }

        Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxWidth()) {
            ElevatedCard(
                modifier = modifier.padding(16.dp).fillMaxWidth()
            ) {
                Row(modifier = modifier.padding(10.dp)) {
                    Image(
                        contentDescription = "Employee Profile Image",
                        painter = painterResource(id = R.drawable.thanos_img),
                        modifier = modifier.width(100.dp)
                            .height(100.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = modifier.width(16.dp))

                    val popies = FontFamily(
                        Font(R.font.poppins_medium, FontWeight.Normal),
                        Font(R.font.poppins_medium, FontWeight.Normal)
                    )
                    Column( modifier = modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
                        Text(
                            text = "Employee name",
                            fontFamily = popies,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentSize()
                        )

                        Spacer(modifier = modifier.height(3.dp))

                        Text(
                            text = "23114",
                            fontFamily = popies,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentSize()
                        )

                        Spacer(modifier = modifier.height(3.dp))

                        Text(
                            text = "Developer",
                            fontFamily = popies,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                }
            }
        }
    }
}

