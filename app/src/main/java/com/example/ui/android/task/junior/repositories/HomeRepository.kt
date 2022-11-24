package com.example.ui.android.task.junior.repositories

import com.example.ui.android.task.junior.models.ClientDataSource
import javax.inject.Inject

class HomeRepository(@Inject val clientDataSource: ClientDataSource) {


    fun getClient() {
        clientDataSource.getClient()
    }
}