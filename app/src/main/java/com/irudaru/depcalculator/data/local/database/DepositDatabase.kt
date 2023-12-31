package com.irudaru.depcalculator.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.data.deposit.DepositDao

const val DEPOSIT_DATABASE_NAME = "deposit_database"

/**
 * Database that provides CRUD operations and entities
 */
@Database(
    entities = [Deposit::class],
    version = 1,
    exportSchema = false
)
abstract class DepositDatabase: RoomDatabase() {
    abstract fun depositDao(): DepositDao

    companion object {
        @Volatile
        private var Instance: DepositDatabase? = null

        fun getDatabase(context: Context): DepositDatabase {
            // If instance is not null, return it. Otherwise create a new database instance
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, DepositDatabase::class.java, DEPOSIT_DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}