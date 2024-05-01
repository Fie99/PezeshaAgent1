package com.pezesha.agent.data.repository.customers

import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.data.repository.customers.datasource.CustomersDataSource
import com.pezesha.agent.domain.customers.CustomersRepository

class CustomersRepositoryImpl(private val dataSource: CustomersDataSource) : CustomersRepository {

    override suspend fun retrieveCustomers(): Resource<List<Customer>> {
        return dataSource.customersList()
    }
}