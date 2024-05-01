package com.pezesha.agent.data.repository.loan

import com.pezesha.agent.data.models.BizSectorsResponse
import com.pezesha.agent.data.models.CountiesResponse
import com.pezesha.agent.data.models.SubCountiesResponse
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.data.repository.loan.datasource.LoanDataSource
import com.pezesha.agent.domain.loan.LoanRepository

class LoanRepositoryImpl(private val dataSource: LoanDataSource) : LoanRepository {
    override suspend fun retrieveCounties(): Resource<CountiesResponse> {
        return dataSource.retrieveCounties()
    }

    override suspend fun retrieveSubCounties(): Resource<SubCountiesResponse> {
        return dataSource.retrieveSubCounties()
    }

    override suspend fun retrieveBizSectors(): Resource<BizSectorsResponse> {
        return dataSource.retrieveBizSectors()
    }

}
