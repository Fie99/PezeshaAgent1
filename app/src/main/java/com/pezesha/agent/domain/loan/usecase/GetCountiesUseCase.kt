package com.pezesha.agent.domain.loan.usecase

import com.pezesha.agent.data.models.CountiesResponse
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.loan.LoanRepository

class GetCountiesUseCase(private val repository: LoanRepository) {

    suspend fun execute(): Resource<CountiesResponse> {
        return repository.retrieveCounties()
    }
}
