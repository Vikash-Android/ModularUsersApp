package com.app.domain.usecase

import com.app.domain.models.User
import com.app.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): User = userRepository.getUsers()
}