package com.pezesha.agent.data.repository.register.datasource

import com.pezesha.agent.data.remote.utils.Resource

interface RegisterDataSource {

    suspend fun requestConsent(phone: String): Resource<String>

    suspend fun verifyConsent(phone: String): Resource<String>

}