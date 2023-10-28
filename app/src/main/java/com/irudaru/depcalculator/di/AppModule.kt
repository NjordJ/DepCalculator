package com.irudaru.depcalculator.di

import com.irudaru.depcalculator.data.deposit.DepositDao
import com.irudaru.depcalculator.data.deposit.OfflineDepositsRepository
import com.irudaru.depcalculator.data.local.database.DepositDatabase
import org.koin.dsl.module

val appModule = module {
    single<DepositDao> {
        val dataBase = get<DepositDatabase>()
        dataBase.depositDao()
    }
    single<OfflineDepositsRepository> { OfflineDepositsRepository(get()) }
}