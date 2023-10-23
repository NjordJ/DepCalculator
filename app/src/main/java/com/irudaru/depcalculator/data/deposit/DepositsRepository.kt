package com.irudaru.depcalculator.data.deposit

import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides CRUD and retrieve operations of [Deposit] from a data source
 */
interface DepositsRepository {
    /**
     * Retrieve all deposits
     */
    fun getAllDepositsStream(): Flow<List<Deposit>>

    /**
     * Retrieve single deposit
     */
    fun getDepositStream(depositId: Int): Flow<Deposit?>

    /**
     * Insert single deposit
     */
    suspend fun insertDeposit(deposit: Deposit)

    /**
     * Delete single deposit
     */
    suspend fun deleteDeposit(deposit: Deposit)

    /**
     * Update deposit
     */
    suspend fun updateDeposit(deposit: Deposit)
}