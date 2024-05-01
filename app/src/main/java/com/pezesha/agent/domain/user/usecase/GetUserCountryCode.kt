package com.pezesha.agent.domain.user.usecase

import com.pezesha.agent.domain.user.UserRepository

class GetUserCountryCode(private val repository: UserRepository) {

    suspend fun execute(): String {
        return repository.getUserPhoneCode()
    }

}