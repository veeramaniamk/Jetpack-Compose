package com.veera.jetpackcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veera.jetpackcompose.client.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = ApiClient.api

    var loginState = MutableStateFlow("")
    var isLoading = MutableStateFlow(false)
    var loginSuccess = MutableStateFlow(false)

    fun validateAndLogin(userId: String, password: String) {
        // Validate
        val id = userId.toIntOrNull()
        if (id == null) {
            loginState.value = "User ID must be an integer"
            return
        }
        if (password.isEmpty() || password.isBlank()) {
            loginState.value = "Password cannot be empty"
            return
        }

        // API call
        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = repository.login(id, password)
                loginState.value = response["message"].toString()
                loginSuccess.value = true
            } catch (e: Exception) {
                loginState.value = "Error: ${e.message}"
            }
            isLoading.value = false
        }
    }
}