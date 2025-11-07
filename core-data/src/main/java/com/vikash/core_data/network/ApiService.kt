package com.vikash.core_data.network

import com.vikash.core_data.model.UserData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/users")
    suspend fun getUsers(): Response<List<UserData>>
}