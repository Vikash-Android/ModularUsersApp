package com.vikash.core_domain.models



sealed interface User {
    data class Success(
        val userDetails: List<Details>
    ): User

    data class Error(
        val errorType: ErrorType
    ): User
}
enum class ErrorType {
    NoInternet, GenricError
}