package com.app.domain.usecase

import com.app.domain.models.User
import com.app.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUsersUseCase {
    override suspend fun invoke(): User = userRepository.getUsers()
}