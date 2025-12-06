package com.veera.jetpackcompose.presentation.feature.home

import android.provider.CalendarContract
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.veera.jetpackcompose.R
import com.veera.jetpackcompose.client.ApiClient

@Composable
fun Login(modifier: Modifier = Modifier, navController: NavController) {

    val context = LocalContext.current
    // Input states
    var loginId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Error states
    var loginIdError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    var isLoading by remember { mutableStateOf(false) }
    var loginSuccess by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }

    var triggerLogin by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        // Background Image
        Image(
            painter = painterResource(id = R.drawable.ic_login_bg),
            contentDescription = "Page Background",
            modifier = modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            color = MaterialTheme.colorScheme.surface
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {

                // --- Login ID ---
                Text("Login ID", fontWeight = FontWeight.Bold)

                TextField(
                    value = loginId,
                    onValueChange = {
                        loginId = it
                        loginIdError = null   // reset error on typing
                    },
                    singleLine = true,
                    isError = loginIdError != null,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text("Bio ID") }
                )

                // Show error text
                if (loginIdError != null) {
                    Text(
                        text = loginIdError!!,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // --- Password ---
                Text("Password", fontWeight = FontWeight.Bold)

                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = null
                    },
                    singleLine = true,
                    isError = passwordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                // Show error text
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // --- Login Button ---
                Button(
                    onClick = {
                        // Validate
                        var valid = true

                        if (loginId.isBlank()) {
                            loginIdError = "Login ID cannot be empty"
                            valid = false
                        }

                        if (password.isBlank()) {
                            passwordError = "Password cannot be empty"
                            valid = false
                        }

                        if (valid) {
                            triggerLogin = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                    if (isLoading) {
                        Text("Logging in...")
                    }

                    if (loginError != null) {
                        Text("Error: $loginError", color = Color.Red)
                    }

                    if (loginSuccess) {
                        Text("Login Successful")
                        // navController.navigate("home")
                    }
                }

                LaunchedEffect(triggerLogin) {
                    if (triggerLogin) {
                        isLoading = true

                        try {
                            val response = ApiClient.api.login(loginId.toInt(), password)
                            loginSuccess = true     // 🌟 success
                        } catch (e: Exception) {
                            loginError = e.message  // ❌ show error
                        }

                        isLoading = false
                        triggerLogin = false // reset
                    }
                }

            }
        }
    }
}