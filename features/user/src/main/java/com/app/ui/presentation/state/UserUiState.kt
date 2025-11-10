package com.app.ui.presentation.state

import com.app.ui.models.UserUiModel

data class UserUiState(
    val screenState: ScreenState = ScreenState.LOADING,
    val users: List<UserUiModel> = emptyList(),
)

enum class ScreenState {
    LOADING,
    SUCCESS,
    GENRIC_ERROR,
    NETWORK_ERROR
}
