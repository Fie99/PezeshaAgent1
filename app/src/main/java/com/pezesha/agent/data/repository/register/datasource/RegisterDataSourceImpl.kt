package com.pezesha.agent.data.repository.register.datasource

import com.pezesha.agent.data.remote.utils.Resource

class RegisterDataSourceImpl : RegisterDataSource {

    override suspend fun requestConsent(phone: String): Resource<String> {
        return Resource.Success("success")
    }

    override suspend fun verifyConsent(phone: String): Resource<String> {
        return Resource.Success("success")
    }
}
