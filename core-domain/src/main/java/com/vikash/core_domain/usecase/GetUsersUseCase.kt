package com.vikash.core_domain.usecase

import com.vikash.core_domain.models.User

interface GetUsersUseCase {
    suspend operator fun invoke(): List<User>
}