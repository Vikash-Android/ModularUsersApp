package com.app.data.di

import com.app.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val retrofit  = Retrofit.Builder().baseUrl("https://fake-json-api.mock.beeceptor.com").addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideRetrofit() = retrofit.create(ApiService::class.java)
}