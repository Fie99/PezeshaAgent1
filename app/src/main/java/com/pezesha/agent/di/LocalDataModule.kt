package com.pezesha.agent.di

import com.pezesha.agent.data.repository.user.datasource.UserDatasource
import com.pezesha.agent.data.repository.user.datasource.UserDatasourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun providesUserDataSource(): UserDatasource {
        return UserDatasourceImpl()
    }


}