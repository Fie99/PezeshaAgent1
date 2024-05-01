package com.pezesha.agent.data.repository.loan.datasource

import com.pezesha.agent.data.models.BizSectorsResponse
import com.pezesha.agent.data.models.CountiesResponse
import com.pezesha.agent.data.models.SubCountiesResponse
import com.pezesha.agent.data.remote.utils.Resource

interface LoanDataSource {

    suspend fun retrieveCounties(): Resource<CountiesResponse>

    suspend fun retrieveSubCounties(): Resource<SubCountiesResponse>

    suspend fun retrieveBizSectors(): Resource<BizSectorsResponse>

}