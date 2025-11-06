package com.vikash.core_data.repository

import com.vikash.core_domain.models.User
import com.vikash.core_domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    // later we’ll add apiService here
) : UserRepository {
    override suspend fun getUsers(): List<User> {
        // temporary mock
        return listOf(
            User(1, "Vikash", ""),
            User(2, "Rahul", ""),
            User(3, "Sneha", "")
        )
    }
}