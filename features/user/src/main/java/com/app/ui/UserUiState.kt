package com.app.ui

import com.app.domain.models.Details

sealed class UserUiState {
    data object Loading: UserUiState()
    data class Success(val users: List<Details>): UserUiState()
    data class Error(val message: String): UserUiState()
}