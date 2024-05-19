package com.example.composeloginapp.di

import com.example.composeloginapp.data.IUserRepository
import com.example.composeloginapp.data.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUserRepository(repository: UserRepository): IUserRepository

}