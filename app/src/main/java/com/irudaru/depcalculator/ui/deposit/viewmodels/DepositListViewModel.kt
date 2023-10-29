package com.irudaru.depcalculator.ui.deposit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.data.deposit.OfflineDepositsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel to retrieve all items in the Room database
 */
class DepositListViewModel(
    private val depositRepository: OfflineDepositsRepository
) : ViewModel() {

    val depositListUiState: StateFlow<DepositListUiState> =
        depositRepository.getAllDepositsStream()
            .map { deposits -> DepositListUiState(deposits) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DepositListUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for DepositListScreen
 */
data class DepositListUiState(val depositList: List<Deposit> = listOf())