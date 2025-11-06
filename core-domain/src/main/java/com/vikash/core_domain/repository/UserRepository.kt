package com.vikash.core_domain.repository

import com.vikash.core_domain.models.User

interface UserRepository {
    suspend fun getUsers(): List<User>
}