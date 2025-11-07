package com.app.data.remotedatasource

import com.app.data.model.UserData

interface UserRemoteDataSource {
    suspend fun getUsers(): Result<List<UserData>>
}