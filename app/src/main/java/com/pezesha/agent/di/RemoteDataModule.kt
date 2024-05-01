package com.pezesha.agent.di

import com.google.gson.Gson
import com.pezesha.agent.data.remote.interfaces.AuthApi
import com.pezesha.agent.data.repository.auth.datasource.AuthRemoteDatasource
import com.pezesha.agent.data.repository.auth.datasource.AuthRemoteDatasourceImpl
import com.pezesha.agent.data.repository.customers.datasource.CustomersDataSource
import com.pezesha.agent.data.repository.customers.datasource.CustomersRemoteDataSourceImpl
import com.pezesha.agent.data.repository.loan.datasource.LoanDataSource
import com.pezesha.agent.data.repository.loan.datasource.LoanDataSourceImpl
import com.pezesha.agent.data.repository.register.datasource.RegisterDataSource
import com.pezesha.agent.data.repository.register.datasource.RegisterDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    @Singleton
    fun providesAuthRemoteDataSource(
        api: AuthApi
    ): AuthRemoteDatasource {
        return AuthRemoteDatasourceImpl(api)
    }

    @Provides
    @Singleton
    fun providesCustomersDataSource(): CustomersDataSource {
        return CustomersRemoteDataSourceImpl()
    }

    @Provides
    @Singleton
    fun providesRegisterDataSource(): RegisterDataSource {
        return RegisterDataSourceImpl()
    }

@Provides
    @Singleton
    fun providesLoanDataSource(gson: Gson): LoanDataSource {
        return LoanDataSourceImpl(gson)
    }

}
