package com.pezesha.agent.data.repository.customers.datasource

import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.data.remote.utils.Resource

interface CustomersDataSource {

    suspend fun customersList(): Resource<List<Customer>>

}