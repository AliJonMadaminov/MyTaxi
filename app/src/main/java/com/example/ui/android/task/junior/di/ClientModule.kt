package com.example.ui.android.task.junior.di

import com.example.ui.android.task.junior.models.ClientDataSource
import com.example.ui.android.task.junior.models.user.User
import com.example.ui.android.task.junior.models.user.client.BaseClient
import com.example.ui.android.task.junior.models.user.client.Client
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ClientModule {

    @Singleton
    @Binds
    abstract fun provideClientDataSource():ClientDataSource

    @Singleton
    @Binds
    abstract fun provideBaseClient(client: Client):BaseClient

    @ClientUser
    @Singleton
    @Binds
    abstract fun provideUser(baseClient: BaseClient):User

}

@Qualifier
annotation class ClientUser