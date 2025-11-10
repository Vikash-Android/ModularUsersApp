package com.app.data.remotedatasource

import com.app.data.model.UserData
import com.app.data.network.ApiService
import com.app.data.network.mapToResult
import com.app.data.network.safeApiCall
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): UserRemoteDataSource  {
    override suspend fun getUsers(): Result<List<UserData>> = safeApiCall { apiService.getUsers().mapToResult()  }


}