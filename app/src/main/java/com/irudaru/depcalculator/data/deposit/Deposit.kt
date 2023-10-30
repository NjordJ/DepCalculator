package com.irudaru.depcalculator.data.deposit

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

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
//    @ColumnInfo(name = "deposit_period_type") val depositPeriodType: DepositType,
//    @ColumnInfo(name = "deposit_period") val depositPeriod: Double,
    @ColumnInfo(name = "last_calculation") val lastCalculation: Double
)

//@Entity(tableName = "deposit_type")
//data class DepositType(
//    @PrimaryKey
//    @ColumnInfo(name = "id_deposit_type")
//    val idDepositType: Int,
//    @ColumnInfo(name = "id_deposit_owner") val idDepositOwner: Int,
//    @ColumnInfo(name = "deposit_type_number") val depositTypeNumber: Int
//)
//
//data class DepositAndDepositType(
//    @Embedded val deposit: Deposit,
//    @Relation(
//        parentColumn = "id_deposit",
//        entityColumn = "id_deposit_owner"
//    )
//    val depositType: DepositType
//)