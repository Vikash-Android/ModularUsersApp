package com.app.data.repository

import com.app.data.model.UserData
import com.app.data.remotedatasource.UserRemoteDataSource
import com.app.domain.models.Details
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import com.app.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUsers(): User =
        withContext(Dispatchers.IO) {
            remoteDataSource.getUsers().fold(
                onSuccess = { users ->
                    User.Success(users.map { it.toUser() })
                },
                onFailure = {
                    User.Error(errorType = ErrorType.GenricError)
                }
            )
        }

    private fun UserData.toUser() = with(this) {
        Details(
            id = id,
            name = name,
            email = email,
            photo = photo,
            company = company,
            country = country,
            phone = phone,
            username = username
        )
    }
}