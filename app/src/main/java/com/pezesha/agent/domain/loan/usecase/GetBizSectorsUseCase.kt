package com.pezesha.agent.domain.loan.usecase

import com.pezesha.agent.data.models.BizSectorsResponse
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.loan.LoanRepository

class GetBizSectorsUseCase(private val repository: LoanRepository) {

    suspend fun execute(): Resource<BizSectorsResponse>{
        return repository.retrieveBizSectors()
    }

}
