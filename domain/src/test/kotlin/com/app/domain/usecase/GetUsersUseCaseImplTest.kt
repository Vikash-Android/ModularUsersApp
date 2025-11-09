package com.app.domain.usecase

import com.app.domain.models.Details
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import com.app.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUsersUseCaseImplTest {

    private val repository = mockk<UserRepository>()
    private lateinit var userUseCase: GetUsersUseCaseImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke returns success when repository returns Users`() = runTest(testDispatcher.scheduler){
        val users = listOf(
            Details(
                id = 1,
                name = "vikash",
                email = "william.a.wheeler@example-pet-store.com",
                photo = "https://picsum.photos/200/300",
                company = "company",
                country = "country",
                phone = "phone",
                username = "username"
            ),
            Details(
                id = 2,
                name = "vikash2",
                email = "james.k.polk@examplepetstore.com",
                photo = "https://picsum.photos/200/300",
                company = "company",
                country = "country",
                phone = "phone",
                username = "username"
            )
        )
        coEvery { repository.getUsers() } returns User.Success(users)

        userUseCase = GetUsersUseCaseImpl(repository)
        val result = userUseCase.invoke()
        testScheduler.advanceUntilIdle()
        assertTrue(result is User.Success)

        val successState = result as User.Success
        assertEquals(2, successState.userDetails.size)
        assertEquals(users[0].name, successState.userDetails[0].name)
    }

    @Test
    fun `invoke returns error when repository fail`() = runTest(testDispatcher.scheduler) {
        coEvery { repository.getUsers() } returns User.Error(ErrorType.GenricError)

        userUseCase = GetUsersUseCaseImpl(repository)
        val result = userUseCase.invoke()
        testScheduler.advanceUntilIdle()
        assertTrue(result is User.Error)

    }

}