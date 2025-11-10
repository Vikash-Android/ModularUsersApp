package com.app.data.repository

import com.app.data.model.UserData
import com.app.data.remotedatasource.UserRemoteDataSource
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private val remoteDataSource = mockk<UserRemoteDataSource>()
    private lateinit var userRepository: UserRepositoryImpl

    @Before
    fun setup() {
        userRepository = UserRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `get Users returns success when remote data source success`() = runTest {
        val users = listOf(
            UserData(
                id = 1,
                name = "vikash",
                email = "william.a.wheeler@example-pet-store.com",
                photo = "https://picsum.photos/200/300",
                company = "company",
                country = "country",
                phone = "phone",
                username = "username",
                address = "new Delhi",
                zip = "201301",
                state = "UP"
            ),
            UserData(
                id = 2,
                name = "vikash2",
                email = "james.k.polk@examplepetstore.com",
                photo = "https://picsum.photos/200/300",
                company = "company",
                country = "country",
                phone = "phone",
                username = "username",
                address = "new Delhi",
                zip = "201301",
                state = "UP"
            )
        )
        coEvery { remoteDataSource.getUsers() } returns Result.success(users)

        val result = userRepository.getUsers()
        assertTrue(result is User.Success)
        val successState = result as User.Success
        assertEquals(2, successState.userDetails.size)
        assertEquals(users[0].name, successState.userDetails[0].name)
    }

    @Test
    fun `get Users returns error when remote data source fail`() = runTest {
        coEvery { remoteDataSource.getUsers() } returns Result.failure(IOException("Network Error"))

        val result = userRepository.getUsers()
        assertTrue(result is User.Error)
        val errorState = result as User.Error
        assertEquals(ErrorType.GenricError, errorState.errorType)
    }
}