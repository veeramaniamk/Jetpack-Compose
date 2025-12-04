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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
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
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f) // half of the screen height
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
            ) {
                val popies = FontFamily(
                    Font(R.font.poppins_medium, FontWeight.Normal),
                    Font(R.font.poppins_medium, FontWeight.Normal)
                )

                Row(
                    verticalAlignment  = Alignment.CenterVertically,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp, 0.dp, 0.dp, 0.dp)
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
                        fontFamily = popies,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.wrapContentSize()
                            .padding(
                                8.dp,
                                0.dp,
                                0.dp,
                                0.dp
                            ),
                        fontWeight = FontWeight.Bold,
                    )

                }

                Spacer(modifier = modifier.height(20.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Login ID",
                        fontFamily = popies,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.wrapContentSize()
                            .align(Alignment.Start),
                                fontWeight = FontWeight.Bold,
                    )

                    var text by remember { mutableStateOf("") }

                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        singleLine = true,
                        modifier = modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        placeholder = { Text("Bio ID") }
                    )

                    Spacer(modifier = modifier.height(20.dp))

                    Text(
                        text = "Password",
                        fontFamily = popies,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.wrapContentSize()
                            .align(Alignment.Start),
                        fontWeight = FontWeight.Bold,
                    )

                    var password by remember { mutableStateOf("") }

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        singleLine = true,
                        modifier = modifier.fillMaxWidth(),
                        placeholder = { Text("Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    )
                }

                Spacer(modifier = modifier.height(20.dp))

                Button(onClick = {

                }, modifier = Modifier.fillMaxWidth()) {
                    Text("Login")
                }
            }
        }
    }
}