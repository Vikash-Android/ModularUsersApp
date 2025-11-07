package com.app.data.di

import com.app.domain.usecase.GetUsersUseCase
import com.app.domain.usecase.GetUsersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetUsersUseCase(
        impl: GetUsersUseCaseImpl
    ): GetUsersUseCase
}