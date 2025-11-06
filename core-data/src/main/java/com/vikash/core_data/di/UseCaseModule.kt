package com.vikash.core_data.di

import com.vikash.core_domain.usecase.GetUsersUseCase
import com.vikash.core_domain.usecase.GetUsersUseCaseImpl
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