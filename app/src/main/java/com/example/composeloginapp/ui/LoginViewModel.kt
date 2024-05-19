package com.example.composeloginapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<String>>(UIState.Empty)
    val uiState: StateFlow<UIState<String>> = _uiState.asStateFlow()
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            delay(Random.nextLong(1500, 3000))
            if (username.isEmpty()) {
                _uiState.value = UIState.Error("Username is empty")
            } else if (password.length < 8) {
                _uiState.value = UIState.Error("Password is less than 8 characters long")
            } else if (!password.any { it.isLowerCase() }) {
                _uiState.value = UIState.Error("Password does not contain lowercase character")
            } else if (!password.any { it.isUpperCase() }) {
                _uiState.value = UIState.Error("Password does not contain uppercase character")
            } else if (!password.any { it.isDigit() }) {
                _uiState.value = UIState.Error("Password does not contain digit")
            } else {
                _uiState.value = UIState.Success(username)
            }
        }
    }
}