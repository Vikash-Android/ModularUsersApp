package com.vikash.core_domain.usecase

import com.vikash.core_domain.models.User
import com.vikash.core_domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetUsersUseCase {
    override suspend fun invoke(): List<User> = userRepository.getUsers()
}