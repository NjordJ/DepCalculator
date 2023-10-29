package com.irudaru.depcalculator.data.deposit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents single row of Deposit
 */
@Entity(tableName = "deposits")
data class Deposit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_deposit")
    val idDeposit: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "deposit_amount") val depositAmount: Double,
    @ColumnInfo(name = "deposit_percent") val depositPercent: Double,
    @ColumnInfo(name = "last_calculation") val lastCalculation: Double
)