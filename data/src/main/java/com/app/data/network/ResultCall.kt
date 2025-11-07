package com.app.data.network

import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ResultCall<T: Any> (
    private val delegate: Call<T>
): Call<Result<T>> {
    override fun execute(): Response<Result<T>> {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(object: Callback<T>{
            override fun onResponse(call: Call<T?>, response: Response<T?>) {
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.success(body))
                        )
                    } ?: run {
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(HttpException(response)))
                        )
                    }
                } else {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(Result.failure(HttpException(response)))
                    )
                }
            }

            override fun onFailure(call: Call<T?>, t: Throwable) {
                val errorResult = when(t) {
                    is IOException -> Result.failure<t>()
                }
            }

        })
    }

    override fun isExecuted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun isCanceled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun clone(): Call<Result<T>?> {
        TODO("Not yet implemented")
    }

    override fun request(): Request {
        TODO("Not yet implemented")
    }

    override fun timeout(): Timeout {
        TODO("Not yet implemented")
    }
}