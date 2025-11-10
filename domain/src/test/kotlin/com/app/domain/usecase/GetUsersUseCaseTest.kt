package com.app.domain.usecase

import com.app.domain.models.Details
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import com.app.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUsersUseCaseTest {
    private val repository = mockk<UserRepository>()
    private lateinit var userUseCase: GetUsersUseCase

    @Before
    fun setup() {
        userUseCase = GetUsersUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke returns success when repository returns Users`() = runTest{
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

        val expectedResult = User.Success(users)

        coEvery { repository.getUsers() } returns User.Success(users)

        val result = userUseCase.invoke()
        testScheduler.advanceUntilIdle()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke returns error when repository fail`() = runTest {
        coEvery { repository.getUsers() } returns User.Error(ErrorType.GenricError)

        val result = userUseCase.invoke()

        testScheduler.advanceUntilIdle()

        assertEquals(User.Error(ErrorType.GenricError), result)
    }
}