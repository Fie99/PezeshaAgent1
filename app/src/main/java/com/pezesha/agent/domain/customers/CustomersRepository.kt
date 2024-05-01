package com.pezesha.agent.domain.customers

import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.data.remote.utils.Resource

interface CustomersRepository {

    suspend fun retrieveCustomers(): Resource<List<Customer>>

}