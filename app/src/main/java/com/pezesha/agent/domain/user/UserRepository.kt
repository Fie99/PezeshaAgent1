package com.pezesha.agent.domain.user

interface UserRepository {

    suspend fun getUserPhoneCode(): String

    suspend fun setUserPhoneCode(code: String)

}