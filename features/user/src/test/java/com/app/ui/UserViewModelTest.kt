package com.app.ui

import androidx.lifecycle.viewModelScope
import com.app.domain.models.Details
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import com.app.domain.usecase.GetUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class UserViewModelTest {
    private val getUsersUseCase = mockk<GetUsersUseCase>()
    private lateinit var viewModel: UserViewModel

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `Initial state is Loading`() = runTest {
        // Verify that the initial value of `uiState` is `UserUiState.Loading` immediately after the ViewModel is initialized.
        coEvery { getUsersUseCase() } returns User.Success(emptyList())
        viewModel = UserViewModel(getUsersUseCase)
        assertEquals(UserUiState.Loading, viewModel.uiState.value)

    }

    @Test
    fun `fetchUser successful with user data`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns a `User.Success` with a non-empty list of users, 
        // verify that `uiState` transitions from `Loading` to `Success` and contains the correctly mapped `UserUiModel` list.
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
        coEvery { getUsersUseCase() } returns User.Success(users)

        viewModel = UserViewModel(getUsersUseCase)
        viewModel.fetchUser()
        // Wait for coroutine to finish
        testScheduler.advanceUntilIdle()

        val actualState = viewModel.uiState.value
        assertTrue(actualState is UserUiState.Success)
        val successState = actualState as UserUiState.Success
        assertEquals(2, successState.users.size)
        assertEquals("vikash", successState.users[0].name)
        assertEquals("vikash2", successState.users[1].name)

    }

    @Test
    fun `fetchUser successful with empty user list`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns a `User.Success` with an empty list, 
        coEvery { getUsersUseCase() } returns User.Success(emptyList())
        viewModel = UserViewModel(getUsersUseCase)
        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        val actualState = viewModel.uiState.value
        assertTrue(actualState is UserUiState.Success)

        val successStaus = actualState as UserUiState.Success
        assertTrue(successStaus.users.isEmpty())

    }

    @Test
    fun `fetchUser no internet error`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns `User.Error` with `ErrorType.NoInternet`, 
        // verify that `uiState` transitions to `Error` with the specific 'No Internet Connection' message.
        coEvery { getUsersUseCase() } returns User.Error(ErrorType.NoInternet)
        viewModel = UserViewModel(getUsersUseCase)
        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        val actualState = viewModel.uiState.value
        assertTrue(actualState is UserUiState.Error)
        val errorStaus = actualState as UserUiState.Error
        assertEquals("No Internet Connection", errorStaus.message)
    }

    @Test
    fun `fetchUser generic error`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns `User.Error` with `ErrorType.GenricError`, 
        // verify that `uiState` transitions to `Error` with the 'Something went wrong' message.
        coEvery { getUsersUseCase() } returns User.Error(ErrorType.GenricError)
        viewModel = UserViewModel(getUsersUseCase)
        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        val actualState = viewModel.uiState.value
        assertTrue(actualState is UserUiState.Error)

        val errorStaus = actualState as UserUiState.Error
        assertEquals("Something went wrong", errorStaus.message)
    }

    @Test
    fun `fetchUser unexpected exception`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` throws an unexpected `Exception`, 
        // verify that the `try-catch` block catches it and `uiState` transitions to `Error` with a message containing 'Unexpected Error'.
        coEvery { getUsersUseCase() } throws Exception("Crash in repository")

        viewModel = UserViewModel(getUsersUseCase)
        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        val actualState = viewModel.uiState.value
        assertTrue(actualState is UserUiState.Error)

        val errorState = actualState as UserUiState.Error
        assertTrue(errorState.message.contains("Crash in repository"))
        assertTrue(errorState.message.contains("Unexpected Error"))
    }

    @Test
    fun `Multiple fetchUser calls sequence`() = runTest(testDispatcher.scheduler){
        // Call `fetchUser` multiple times in succession. 
        // First, a success, then an error. 
        // Verify that the `uiState` correctly reflects the final state, which should be `Error`.
        val user = Details(
            id = 1,
            name = "vikash",
            email = "william.a.wheeler@example-pet-store.com",
            photo = "https://picsum.photos/200/300",
            company = "company",
            country = "country",
            phone = "phone",
            username = "username"
        )
        coEvery { getUsersUseCase() } returnsMany listOf(
            User.Success(listOf(user)),
            User.Error(ErrorType.NoInternet)
        )
        viewModel = UserViewModel(getUsersUseCase)
        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        val actualState = viewModel.uiState.value
        assertTrue(actualState is UserUiState.Error)

        val errorState = actualState as UserUiState.Error
        assertEquals("No Internet Connection", errorState.message)
    }

    @Test
    fun `ViewModel coroutine cancellation`() = runTest(testDispatcher.scheduler){
        // While `fetchUser` is in a suspended state (simulating a long network call), cancel the `viewModelScope`. 
        // Verify that the `uiState` does not update and remains in the `Loading` state.
        coEvery { getUsersUseCase() } coAnswers {
            delay(1000L)
            User.Success(emptyList())
        }
        viewModel = UserViewModel(getUsersUseCase)
        viewModel.fetchUser()

        viewModel.viewModelScope.cancel()
        testScheduler.advanceUntilIdle()

        val actualState = viewModel.uiState.value
        assertTrue(actualState is UserUiState.Loading)
    }
}