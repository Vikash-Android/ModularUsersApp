package com.app.domain.repository

import com.app.domain.models.User

interface UserRepository {
    suspend fun getUsers(): User
}