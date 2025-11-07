package com.vikash.core_data.repository

import com.vikash.core_data.model.UserData
import com.vikash.core_data.remotedatasource.UserRemoteDataSource
import com.vikash.core_domain.models.Details
import com.vikash.core_domain.models.ErrorType
import com.vikash.core_domain.models.User
import com.vikash.core_domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUsers(): User =

        remoteDataSource.getUsers().fold(
            onSuccess = { users ->
                User.Success( users.map { it.toUser() } )
            },
            onFailure = {
                User.Error(errorType = ErrorType.GenricError)
            }
        )

    private fun UserData.toUser() = with(this) {
        Details(
            id = id,
            name = name,
            email = email
        )
    }
}