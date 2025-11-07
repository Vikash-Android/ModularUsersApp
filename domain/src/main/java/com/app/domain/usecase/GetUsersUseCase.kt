package com.app.domain.usecase

import com.app.domain.models.User

interface GetUsersUseCase {
    suspend operator fun invoke(): User
}