package com.irudaru.depcalculator.ui.deposititem

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.data.deposit.OfflineDepositsRepository
import com.irudaru.depcalculator.ui.deposititem.screens.DepositItemDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DepositItemViewModel(
    savedStateHandle: SavedStateHandle,
    private val depositRepository: OfflineDepositsRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            depositRepository.getDepositStream(depositId)
        }
    }

    private val depositId: Int =
        checkNotNull(savedStateHandle[DepositItemDestination.depositItemIdArg])

    val depositItemUiState: StateFlow<DepositItemUiState> =
        depositRepository.getDepositStream(depositId)
            .filterNotNull()
            .map {
                DepositItemUiState(
                    depositItem = it.toDepositItem()
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DepositItemUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * UI state for an Deposit Item
 */
data class DepositItemUiState(
    val depositItem: DepositItem = DepositItem()
)

data class DepositItem(
    val idDeposit: Int = 0,
    val title: String = "",
    val depositAmount: String = "",
    val depositPercent: String = "",
)

/**
 * Extension function to convert [DepositItem] to [Deposit]
 */
fun DepositItem.toDeposit(): Deposit = Deposit(
    idDeposit = idDeposit,
    title = title,
    depositAmount = depositAmount.toDoubleOrNull() ?: 0.0,
    depositPercent = depositPercent.toDoubleOrNull() ?: 0.0
)

/**
 * Extension function to convert [Deposit] to [DepositItem]
 */
fun Deposit.toDepositItem(): DepositItem = DepositItem(
    idDeposit = idDeposit,
    title = title,
    depositAmount = depositAmount.toString(),
    depositPercent = depositPercent.toString()
)

/**
 * Extension function to convert [Deposit] to [DepositItemUiState]
 */
fun Deposit.toDepositItemUiState(): DepositItemUiState = DepositItemUiState(
    depositItem = this.toDepositItem()
)