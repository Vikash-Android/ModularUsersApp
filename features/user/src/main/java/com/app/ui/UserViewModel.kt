package com.app.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.models.ErrorType
import com.app.domain.models.User
import com.app.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()
    init {
        fetchUser()
    }

    fun fetchUser() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading

            try {
                when(val result = getUsersUseCase()) {
                    is User.Success -> {
                        _uiState.value = UserUiState.Success(result.userDetails)
                    }

                    is User.Error -> {
                        _uiState.value = when(result.errorType) {
                            ErrorType.NoInternet -> UserUiState.Error("No Internet Connection")
                            ErrorType.GenricError -> UserUiState.Error("Something went wrong")
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UserUiState.Error("Unexpected Error: ${e.message}")
            }
        }
    }
}