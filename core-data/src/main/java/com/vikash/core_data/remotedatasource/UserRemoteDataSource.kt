package com.vikash.core_data.remotedatasource

import com.vikash.core_data.model.UserData

interface UserRemoteDataSource {
    suspend fun getUsers(): Result<List<UserData>>
}