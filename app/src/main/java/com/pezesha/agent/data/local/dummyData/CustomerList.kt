package com.pezesha.agent.data.local.dummyData

class CustomerList(
    val id: Int,
    val customerName: String,
    val businessName: String,
    val status: String,
)

val CUSTOMER_LIST = listOf(
    CustomerList(
        id = 1,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Incomplete",
    ),
    CustomerList(
        id = 2,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 3,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 4,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 5,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 6,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 7,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 8,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 9,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
    CustomerList(
        id = 10,
        customerName = "Customer name",
        businessName = "Business name",
        status = "Completed",
    ),
)

fun List<CustomerList>.getCustomerList(id: Int) =
    find { it.id == id } ?: error("Customer list not found!")
