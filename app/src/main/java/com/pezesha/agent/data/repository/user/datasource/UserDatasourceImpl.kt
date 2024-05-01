package com.pezesha.agent.data.repository.user.datasource

import com.pezesha.agent.PezeshaAgentApp

class UserDatasourceImpl : UserDatasource {

    override suspend fun getUserPhoneCode(): String {
        return PezeshaAgentApp.sharedPrefsManager.countryCode
    }

    override suspend fun setUserPhoneCode(code: String) {
        PezeshaAgentApp.sharedPrefsManager.countryCode = code
    }
}