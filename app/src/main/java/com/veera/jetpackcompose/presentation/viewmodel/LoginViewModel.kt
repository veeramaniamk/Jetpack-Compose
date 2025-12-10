package com.veera.jetpackcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPwd: String) {
        _uiState.value = _uiState.value.copy(password = newPwd)
    }

    fun login() {
        val state = _uiState.value
        if (state.email.isEmpty() || state.password.isEmpty()) {
            _uiState.value = state.copy(error = "Please fill all fields")
            return
        }

        // Start loading
        _uiState.value = state.copy(isLoading = true, error = null)

        // Fake API delay
        viewModelScope.launch {
            delay(2000)

            // SUCCESS (You can replace with real API)
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = null
            )
        }
    }
}
