package com.pezesha.agent.di

import com.pezesha.agent.domain.auth.AuthRepository
import com.pezesha.agent.domain.auth.usecase.GetLoginUseCase
import com.pezesha.agent.domain.customers.CustomersRepository
import com.pezesha.agent.domain.customers.usecase.GetCustomersUseCase
import com.pezesha.agent.domain.loan.LoanRepository
import com.pezesha.agent.domain.loan.usecase.GetBizSectorsUseCase
import com.pezesha.agent.domain.loan.usecase.GetCountiesUseCase
import com.pezesha.agent.domain.loan.usecase.GetSubCountiesUseCase
import com.pezesha.agent.domain.register.RegisterRepository
import com.pezesha.agent.domain.register.useCase.GetRequestConsentUseCase
import com.pezesha.agent.domain.register.useCase.GetVerifyConsentUseCase
import com.pezesha.agent.domain.user.UserRepository
import com.pezesha.agent.domain.user.usecase.GetUserCountryCode
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetLoginUseCase(repository: AuthRepository): GetLoginUseCase =
        GetLoginUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCustomerUseCase(repository: CustomersRepository): GetCustomersUseCase =
        GetCustomersUseCase(repository)

    @Provides
    @Singleton
    fun provideGetUserCountryCode(repository: UserRepository): GetUserCountryCode =
        GetUserCountryCode(repository)

    @Provides
    @Singleton
    fun provideGetSendConsentUseCase(repository: RegisterRepository): GetRequestConsentUseCase =
        GetRequestConsentUseCase(repository)

    @Provides
    @Singleton
    fun provideGetVerifyConsentUseCase(repository: RegisterRepository): GetVerifyConsentUseCase =
        GetVerifyConsentUseCase(repository)

    @Provides
    @Singleton
    fun provideGetBizSectorsUseCase(repository: LoanRepository): GetBizSectorsUseCase =
        GetBizSectorsUseCase(repository)

    @Provides
    @Singleton
    fun provideGetCountiesUseCase(repository: LoanRepository): GetCountiesUseCase =
        GetCountiesUseCase(repository)

    @Provides
    @Singleton
    fun provideGetSubCountiesUseCase(repository: LoanRepository): GetSubCountiesUseCase =
        GetSubCountiesUseCase(repository)

}
