package com.app.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.models.Details
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import com.app.domain.usecase.GetUsersUseCase
import com.app.ui.models.UserUiModel
import com.app.ui.presentation.state.ScreenState
import com.app.ui.presentation.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UserUiState> = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    internal fun fetchUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(screenState = ScreenState.LOADING) }
            when (val result = getUsersUseCase()) {
                is User.Success -> {
                    _uiState.update {
                        it.copy(
                            screenState = ScreenState.SUCCESS,
                            users = result.userDetails.map { user -> user.toUiUser() }
                        )
                    }
                }

                is User.Error -> handleError(result.errorType)
            }
        }
    }

    private fun handleError(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NoInternet -> _uiState.update { it.copy(screenState = ScreenState.NETWORK_ERROR) }
            ErrorType.GenricError -> _uiState.update { it.copy(screenState = ScreenState.GENRIC_ERROR) }
        }
    }

    private fun Details.toUiUser() = with(this) {
        UserUiModel(
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