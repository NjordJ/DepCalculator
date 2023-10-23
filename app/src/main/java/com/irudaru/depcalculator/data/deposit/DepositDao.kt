package com.irudaru.depcalculator.data.deposit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * CRUD operations for database with [Deposit]
 */
@Dao
interface DepositDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(deposit: Deposit)

    @Update
    suspend fun update(deposit: Deposit)

    @Delete
    suspend fun delete(deposit: Deposit)

    @Query("SELECT * FROM deposits WHERE id_deposit = :depositId")
    fun getDeposit(depositId: Int): Flow<Deposit>

    @Query("SELECT * FROM deposits ORDER BY title ASC")
    fun getAllDeposits(): Flow<List<Deposit>>
}