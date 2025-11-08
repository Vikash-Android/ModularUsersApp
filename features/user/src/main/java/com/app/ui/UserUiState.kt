package com.app.ui

import com.app.domain.models.Details
import com.app.ui.models.UserUiModel

sealed class UserUiState {
    data object Loading: UserUiState()
    data class Success(val users: List<UserUiModel>): UserUiState()
    data class Error(val message: String): UserUiState()
}