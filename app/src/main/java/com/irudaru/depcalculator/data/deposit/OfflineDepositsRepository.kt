package com.irudaru.depcalculator.data.deposit

import kotlinx.coroutines.flow.Flow

class OfflineDepositsRepository(
    private val depositDao: DepositDao
): DepositsRepository {

    override fun getAllDepositsStream(): Flow<List<Deposit>> = depositDao.getAllDeposits()

    override fun getDepositStream(depositId: Int): Flow<Deposit?> = depositDao.getDeposit(depositId)

    override suspend fun insertDeposit(deposit: Deposit) = depositDao.insert(deposit)

    override suspend fun deleteDeposit(deposit: Deposit) = depositDao.delete(deposit)

    override suspend fun updateDeposit(deposit: Deposit) = depositDao.update(deposit)
}