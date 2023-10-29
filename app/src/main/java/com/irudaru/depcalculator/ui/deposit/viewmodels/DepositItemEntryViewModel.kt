package com.irudaru.depcalculator.ui.deposit.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.irudaru.depcalculator.data.deposit.OfflineDepositsRepository

class DepositItemEntryViewModel(
    private val depositRepository: OfflineDepositsRepository
) : ViewModel() {

    /**
     * Holds current item ui state
     */
    var depositItemUiState by mutableStateOf(DepositItemUiState())
        private set

    /**
     * Updates the [depositItemUiState] with the value provided in the argument. This method also triggers
     * a validation for input values.
     */
    fun updateUiState(depositItem: DepositItem) {
        depositItemUiState =
            DepositItemUiState(depositItem = depositItem)
    }

    suspend fun saveDepositItem() = depositRepository.insertDeposit(
        depositItemUiState.depositItem.toDeposit()
    )
}