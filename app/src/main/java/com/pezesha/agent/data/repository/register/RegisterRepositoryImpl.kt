package com.pezesha.agent.data.repository.register

import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.data.repository.register.datasource.RegisterDataSource
import com.pezesha.agent.domain.register.RegisterRepository

class RegisterRepositoryImpl(private val dataSource: RegisterDataSource) : RegisterRepository {

    override suspend fun sendConsent(phone: String): Resource<String> {
        return dataSource.requestConsent(phone)
    }

    override suspend fun verifyConsent(phone: String): Resource<String> {
        return dataSource.verifyConsent(phone)
    }

}
