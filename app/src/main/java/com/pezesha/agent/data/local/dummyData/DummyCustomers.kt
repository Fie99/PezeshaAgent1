package com.pezesha.agent.data.local.dummyData

data class Customer(
    val id: Int,
    val customerName: String,
    val businessName: String,
    val isRegistrationComplete: Int,
)

val RECENT_CUSTOMERS = listOf(
    Customer(
        id = 1,
        customerName = "John Snow",
        businessName = "White Walkers",
        isRegistrationComplete = 0,
    ),
    Customer(
        id = 2,
        customerName = "Geralt Of Rivia",
        businessName = "Witcher",
        isRegistrationComplete = 1,
    ),
    Customer(
        id = 3,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 0,
    ),
    Customer(
        id = 4,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 1,
    ),
    Customer(
        id = 5,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 1,
    ),
    Customer(
        id = 6,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 1,
    ),
    Customer(
        id = 7,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 1,
    ),
    Customer(
        id = 8,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 1,
    ),
    Customer(
        id = 9,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 1,
    ),
    Customer(
        id = 10,
        customerName = "Customer name",
        businessName = "Business name",
        isRegistrationComplete = 1,
    ),
)

fun List<Customer>.getRecentBusiness(id: Int) =
    find { it.id == id } ?: error("Recent business not found!")
