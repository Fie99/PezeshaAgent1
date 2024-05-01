package com.pezesha.agent.data.repository.user.datasource

interface UserDatasource {

    suspend fun getUserPhoneCode(): String

    suspend fun setUserPhoneCode(code: String)

}