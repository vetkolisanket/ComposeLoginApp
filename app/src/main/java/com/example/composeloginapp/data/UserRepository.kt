package com.example.composeloginapp.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor() : IUserRepository {

    private val usernameFlow = MutableStateFlow("")
    override fun isUserLoggedIn(): Flow<Boolean> = flow {
        emit(usernameFlow.value.isNotBlank())
    }

    override suspend fun saveUserInfo(username: String) {
        withContext(Dispatchers.IO) {
            usernameFlow.value = username
        }
    }

    override fun getUserInfo(): Flow<String> = flow {
        emit(usernameFlow.value)
    }
}