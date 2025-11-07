package com.app.data.network

import retrofit2.Response

fun <T> Response<T>.mapToResult(): Result<T> = this.let {
    if (isSuccessful) {
        Result.success(body() ?: throw IllegalArgumentException())
    } else {
        Result.failure(exception = Exception(message()))
    }
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Result<T>): Result<T> {
    return try {

        // 2️⃣ Call the API (which returns Result<T>)
        val result = apiCall()

        // 3️⃣ Return as-is (success or failure)
        result
    } catch (e: Exception) {
        // 4️⃣ Catch unexpected exceptions globally
        Result.failure(e)
    }
}
