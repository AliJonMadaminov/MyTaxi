package com.example.ui.android.task.junior.di

import com.example.ui.android.task.junior.models.user.User
import com.example.ui.android.task.junior.models.user.driver.BaseDriver
import com.example.ui.android.task.junior.models.user.driver.Driver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Qualifier

@InstallIn(FragmentComponent::class)
@Module
abstract class DriverModule {

    @FragmentScoped
    @Binds
    abstract fun provideBaseDriver(driver: Driver): BaseDriver


    @DriverUser
    @FragmentScoped
    @Binds
    abstract fun provideUser(baseDriver: BaseDriver): User

}

@Qualifier
annotation class DriverUser