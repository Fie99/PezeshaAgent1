package com.pezesha.agent.data.repository.user

import com.pezesha.agent.data.repository.user.datasource.UserDatasource
import com.pezesha.agent.domain.user.UserRepository

class UserRepositoryImpl(private val datasource: UserDatasource) : UserRepository {

    override suspend fun getUserPhoneCode(): String {
        return datasource.getUserPhoneCode()
    }

    override suspend fun setUserPhoneCode(code: String) {
        datasource.setUserPhoneCode(code)
    }

}
