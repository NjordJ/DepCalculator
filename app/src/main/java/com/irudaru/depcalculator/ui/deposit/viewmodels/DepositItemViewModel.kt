package com.irudaru.depcalculator.ui.deposit.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.data.deposit.OfflineDepositsRepository
import com.irudaru.depcalculator.ui.deposit.screens.DepositItemDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DepositItemViewModel(
    savedStateHandle: SavedStateHandle,
    private val depositRepository: OfflineDepositsRepository
) : ViewModel() {

    init {
        val depositId: Int =
            checkNotNull(savedStateHandle[DepositItemDestination.depositItemIdArg])

        viewModelScope.launch {
            depositItemUiState = depositRepository.getDepositStream(depositId)
                .filterNotNull()
                .first()
                .toDepositItemUiState()
        }
    }


    /**
     * Holds current deposit item ui state
     */
    var depositItemUiState by mutableStateOf(DepositItemUiState())
        private set

    fun updateUiState(depositItem: DepositItem) {
        depositItemUiState =
            DepositItemUiState(depositItem = depositItem)
    }


    suspend fun updateDepositItem() = depositRepository.updateDeposit(
        depositItemUiState.depositItem.toDeposit()
    )

    suspend fun deleteDepositItem() = depositRepository.deleteDeposit(
        depositItemUiState.depositItem.toDeposit()
    )

//    init {
//        val depositId: Int =
//            checkNotNull(savedStateHandle[DepositItemDestination.depositItemIdArg])
//        Log.d("DepositItemViewModel", "${depositId}")
//
//        viewModelScope.launch {
//            depositItemUiState = MutableStateFlow(
//                DepositItemUiState(
//                    depositItem = depositRepository.getDepositStream(depositId)
//                        .filterNotNull()
//                        .map {
//                            it.toDepositItem()
//                        }.first()
//                )
//            )
//            Log.d("DepositItemViewModel", "${depositItemUiState.value.depositItem}")
//        }
//
//    }
//
//
//    var depositItemUiState: MutableStateFlow<DepositItemUiState> = MutableStateFlow(
//        DepositItemUiState()
//    )
//
//    fun updateUiState(depositItem: DepositItem) {
//        depositItemUiState.value = depositItemUiState.value.copy(
//            depositItem = depositItem
//        )
//    }
//
//    suspend fun updateDepositItem() {
//        //depositRepository.updateDeposit(depositItemUiState.value.depositItem.toDeposit())
//    }
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
    val lastCalculation: String = ""
)

/**
 * Extension function to convert [DepositItem] to [Deposit]
 */
fun DepositItem.toDeposit(): Deposit = Deposit(
    idDeposit = idDeposit,
    title = title,
    depositAmount = depositAmount.toDoubleOrNull() ?: 0.0,
    depositPercent = depositPercent.toDoubleOrNull() ?: 0.0,
    lastCalculation = lastCalculation.toDoubleOrNull() ?: 0.0
)

/**
 * Extension function to convert [Deposit] to [DepositItem]
 */
fun Deposit.toDepositItem(): DepositItem = DepositItem(
    idDeposit = idDeposit,
    title = title,
    depositAmount = depositAmount.toString(),
    depositPercent = depositPercent.toString(),
    lastCalculation = lastCalculation.toString()
)

/**
 * Extension function to convert [Deposit] to [DepositItemUiState]
 */
fun Deposit.toDepositItemUiState(): DepositItemUiState = DepositItemUiState(
    depositItem = this.toDepositItem()
)