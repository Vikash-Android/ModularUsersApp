package com.app.data.di

import com.app.data.remotedatasource.UserRemoteDataSource
import com.app.data.remotedatasource.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteUserDataSource(
        impl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource
}