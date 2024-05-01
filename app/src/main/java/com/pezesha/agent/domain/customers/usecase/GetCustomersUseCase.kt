package com.pezesha.agent.domain.customers.usecase

import com.pezesha.agent.data.local.dummyData.Customer
import com.pezesha.agent.data.remote.utils.Resource
import com.pezesha.agent.domain.customers.CustomersRepository

class GetCustomersUseCase(private val repository: CustomersRepository) {

    suspend fun execute(): Resource<List<Customer>> {
        return repository.retrieveCustomers()
    }


}