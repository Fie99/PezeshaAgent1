package com.pezesha.agent.data.repository.customers.datasource

import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.data.local.dummyData.RECENT_CUSTOMERS
import com.pezesha.agent.data.remote.utils.Resource

class CustomersRemoteDataSourceImpl : CustomersDataSource {

    override suspend fun customersList(): Resource<List<Customer>> {
        return Resource.Success(data = RECENT_CUSTOMERS)
    }

}