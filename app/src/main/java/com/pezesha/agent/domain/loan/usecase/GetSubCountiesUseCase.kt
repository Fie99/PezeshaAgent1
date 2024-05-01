package com.pezesha.agent.domain.loan.usecase

import com.pezesha.agent.data.models.SubCountiesResponse
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.loan.LoanRepository

class GetSubCountiesUseCase(private val repository: LoanRepository) {

    suspend fun execute(): Resource<SubCountiesResponse> {
        return repository.retrieveSubCounties()
    }

}
