package com.example.composeloginapp.ui

import androidx.lifecycle.ViewModel
import com.example.composeloginapp.data.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: IUserRepository) : ViewModel() {

    fun isUserLoggedIn(): Flow<Boolean> {
        return repository.isUserLoggedIn()
    }

}