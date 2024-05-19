package com.example.composeloginapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.composeloginapp.R
import com.example.composeloginapp.ui.LoginViewModel
import com.example.composeloginapp.ui.UIState

@Composable
fun LoginScreen(navController: NavHostController) {
    val localFocusManager = LocalFocusManager.current
    val viewModel: LoginViewModel = viewModel()
    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val state: UIState<String> by viewModel.uiState.collectAsStateWithLifecycle()
    val showProgress = remember {
        mutableStateOf(false)
    }
    when (state) {
        is UIState.Error -> {
            showProgress.value = false
            LaunchedEffect(key1 = Unit) {
                snackbarHostState.showSnackbar((state as UIState.Error).message)
            }
        }

        UIState.Loading -> showProgress.value = true
        is UIState.Success -> {
            showProgress.value = false
            navController.navigate("home")
        }

        else -> Unit
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = username.value,
                    onValueChange = { value -> username.value = value },
                    label = { Text(text = stringResource(R.string.username)) },
                    placeholder = { Text(text = "Enter username") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = password.value,
                    onValueChange = { value -> password.value = value },
                    label = { Text(text = stringResource(R.string.password)) },
                    placeholder = { Text(text = "Enter password") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    maxLines = 1,
                    keyboardActions = KeyboardActions {
                        localFocusManager.clearFocus()
                    }
                )
                Text(
                    text = "Password must be at least 8 characters long",
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Password must contain a lowercase, uppercase and a digit character",
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.login(username.value, password.value) }
                ) {
                    Text(text = "Login")
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (showProgress.value) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}