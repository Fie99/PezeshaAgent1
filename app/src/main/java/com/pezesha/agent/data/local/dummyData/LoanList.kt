package com.pezesha.agent.data.local.dummyData

class LoanList (
    val id: Int,
    val customerName: String,
    val loanAmount: String,
    val loanStatus: String,
        )
val LOAN_LIST= listOf(
    LoanList(
        id=1,
        customerName = "John Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 187,000",
    ),
    LoanList(
        id = 2,
        customerName = "Alex Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 45,000"
    ),
    LoanList(
        id = 3,
        customerName = "Paul Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 677,000"
    ),
    LoanList(
        id = 4,
        customerName = "Johny Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 78,000"
    ),
    LoanList(
        id = 5,
        customerName = "Mary Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 232,000"
    ),
    LoanList(
        id = 6,
        customerName = "John Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 433,000"
    ),
    LoanList(
        id = 7,
        customerName = "John Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 18,000"
    ),
    LoanList(
        id = 8,
        customerName = "John Doe",
        loanAmount = "KSH 300,000",
        loanStatus = "- KSH 23,000"
    ),
)
fun List<CustomerList>.getLoanList(id: Int) =
    find { it.id == id } ?: error("Loan list not found!")