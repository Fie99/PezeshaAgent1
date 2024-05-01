package com.pezesha.agent.data.repository.loan.datasource

import com.google.gson.Gson
import com.pezesha.agent.data.local.dummyData.BIZSECTOR
import com.pezesha.agent.data.local.dummyData.COUNTIES
import com.pezesha.agent.data.local.dummyData.SUBCOUNTIES
import com.pezesha.agent.data.models.BizSectorsResponse
import com.pezesha.agent.data.models.CountiesResponse
import com.pezesha.agent.data.models.SubCountiesResponse
import com.pezesha.agent.data.remote.utils.Resource

class LoanDataSourceImpl(private val gson: Gson) : LoanDataSource {

    override suspend fun retrieveCounties(): Resource<CountiesResponse> {
        val res = gson.fromJson(COUNTIES, CountiesResponse::class.java)
        return Resource.Success(res)
    }

    override suspend fun retrieveSubCounties(): Resource<SubCountiesResponse> {
        val res = gson.fromJson(SUBCOUNTIES, SubCountiesResponse::class.java)
        return Resource.Success(res)
    }

    override suspend fun retrieveBizSectors(): Resource<BizSectorsResponse> {
        val res = gson.fromJson(BIZSECTOR, BizSectorsResponse::class.java)
        return Resource.Success(res)
    }
}
