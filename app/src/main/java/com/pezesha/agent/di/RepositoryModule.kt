package com.pezesha.agent.di

import com.pezesha.agent.data.repository.auth.AuthRepositoryImpl
import com.pezesha.agent.data.repository.auth.datasource.AuthRemoteDatasource
import com.pezesha.agent.data.repository.customers.CustomersRepositoryImpl
import com.pezesha.agent.data.repository.customers.datasource.CustomersDataSource
import com.pezesha.agent.data.repository.loan.LoanRepositoryImpl
import com.pezesha.agent.data.repository.loan.datasource.LoanDataSource
import com.pezesha.agent.data.repository.register.RegisterRepositoryImpl
import com.pezesha.agent.data.repository.register.datasource.RegisterDataSource
import com.pezesha.agent.data.repository.user.UserRepositoryImpl
import com.pezesha.agent.data.repository.user.datasource.UserDatasource
import com.pezesha.agent.domain.auth.AuthRepository
import com.pezesha.agent.domain.customers.CustomersRepository
import com.pezesha.agent.domain.loan.LoanRepository
import com.pezesha.agent.domain.register.RegisterRepository
import com.pezesha.agent.domain.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authRemoteDatasource: AuthRemoteDatasource): AuthRepository {
        return AuthRepositoryImpl(authRemoteDatasource)
    }

    @Provides
    @Singleton
    fun provideCustomerRepository(datasource: CustomersDataSource): CustomersRepository {
        return CustomersRepositoryImpl(datasource)
    }

    @Provides
    @Singleton
    fun provideUserRepository(datasource: UserDatasource): UserRepository {
        return UserRepositoryImpl(datasource)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(datasource: RegisterDataSource): RegisterRepository {
        return RegisterRepositoryImpl(datasource)
    }

    @Provides
    @Singleton
    fun provideLoanRepository(datasource: LoanDataSource): LoanRepository {
        return LoanRepositoryImpl(datasource)
    }

}
