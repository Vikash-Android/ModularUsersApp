package com.app.ui

import com.app.domain.models.Details
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import com.app.domain.usecase.GetUsersUseCase
import com.app.ui.models.UserUiModel
import com.app.ui.presentation.UserViewModel
import com.app.ui.presentation.state.ScreenState
import com.app.ui.presentation.state.UserUiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        viewModel = UserViewModel(getUsersUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    // Verify that the initial value of `uiState` is `UserUiState.Loading` immediately after the ViewModel is initialized.
    @Test
    fun `Initial state is Loading`() = runTest {
        coEvery { getUsersUseCase() } returns User.Success(emptyList())

        viewModel = UserViewModel(getUsersUseCase)

        assertEquals(UserUiState(), viewModel.uiState.value)
    }

    @Test
    fun `fetchUser successful with user data`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns a `User.Success` with a non-empty list of users, 
        // verify that `uiState` transitions from `Loading` to `Success` and contains the correctly mapped `UserUiModel` list.
        val expectedState = UserUiState(
            screenState = ScreenState.SUCCESS,
            users = userUiList
        )
        coEvery { getUsersUseCase() } returns User.Success(users)

        viewModel.fetchUser()

        // Wait for coroutine to finish
        testScheduler.advanceUntilIdle()

        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetchUser successful with empty user list`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns a `User.Success` with an empty list, 
        val expectedState = UserUiState(
            screenState = ScreenState.SUCCESS,
            users = emptyList()
        )

        coEvery { getUsersUseCase() } returns User.Success(emptyList())

        viewModel.fetchUser()

        testScheduler.advanceUntilIdle()

        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetchUser no internet error`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns `User.Error` with `ErrorType.NoInternet`, 
        // verify that `uiState` transitions to `Error` with the specific 'No Internet Connection' message.
        val expectedState = UserUiState(screenState = ScreenState.NETWORK_ERROR)

        coEvery { getUsersUseCase() } returns User.Error(ErrorType.NoInternet)

        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        assertEquals(expectedState, viewModel.uiState.value)
    }

    @Test
    fun `fetchUser generic error`() = runTest(testDispatcher.scheduler) {
        // When `getUsersUseCase` returns `User.Error` with `ErrorType.GenricError`, 
        // verify that `uiState` transitions to `Error` with the 'Something went wrong' message.
        val expectedState = UserUiState(screenState = ScreenState.GENRIC_ERROR)

        coEvery { getUsersUseCase() } returns User.Error(ErrorType.GenricError)

        viewModel.fetchUser()
        testScheduler.advanceUntilIdle()

        assertEquals(expectedState, viewModel.uiState.value)
    }

    companion object {
        private val users = listOf(
            Details(
                id = 1,
                name = "vikash",
                email = "william.a.wheeler@example-pet-store.com",
                photo = "https://picsum.photos/200/300",
                company = "company",
                country = "country",
                phone = "phone",
                username = "username"
            )
        )

        private val userUiList = listOf(
            UserUiModel(
                id = 1,
                name = "vikash",
                email = "william.a.wheeler@example-pet-store.com",
                photo = "https://picsum.photos/200/300",
                company = "company",
                country = "country",
                phone = "phone",
                username = "username"
            )
        )
    }
}