package com.vikash.core_data.di

import com.vikash.core_data.network.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {
    private val retrofit  = Retrofit.Builder().baseUrl("https://fake-json-api.mock.beeceptor.com").addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun provideRetrofit() = retrofit.create(ApiService::class.java)
}