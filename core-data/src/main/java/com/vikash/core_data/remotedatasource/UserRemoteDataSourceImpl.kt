package com.vikash.core_data.remotedatasource

import com.vikash.core_data.model.UserData
import com.vikash.core_data.network.ApiService
import com.vikash.core_data.network.mapToResult
import com.vikash.core_data.network.safeApiCall
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService
): UserRemoteDataSource  {
    override suspend fun getUsers(): Result<List<UserData>> = safeApiCall { apiService.getUsers().mapToResult()  }


}