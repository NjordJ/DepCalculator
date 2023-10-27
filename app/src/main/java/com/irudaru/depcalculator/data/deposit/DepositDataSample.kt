package com.irudaru.depcalculator.data.deposit

object DepositListDataSample {
    val depositList = listOf(
        Deposit(
            idDeposit = 1,
            title = "Deposit Plus",
            depositAmount = 4000.00,
            depositPercent = 100.0
        ),
        Deposit(
            idDeposit = 2,
            title = "Deposit Extra",
            depositAmount = 15000.00,
            depositPercent = 5.0
        ),
        Deposit(
            idDeposit = 3,
            title = "Deposit Plus++",
            depositAmount = 7000.00,
            depositPercent = 13.0
        ),
        Deposit(
            idDeposit = 4,
            title = "Deposit Normal",
            depositAmount = 7000.00,
            depositPercent = 2.0
        ),
    )
}

object DepositItemDataSample {
    val depositItem = Deposit(
        idDeposit = 1,
        title = "Deposit Plus",
        depositAmount = 200000.00,
        depositPercent = 3.0
    )
}