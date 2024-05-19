package com.example.composeloginapp.data

import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun isUserLoggedIn(): Flow<Boolean>

    suspend fun saveUserInfo(username: String)

    fun getUserInfo(): Flow<String>

}