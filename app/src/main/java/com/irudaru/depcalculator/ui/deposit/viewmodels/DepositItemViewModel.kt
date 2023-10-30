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
import kotlin.math.round

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
            DepositItemUiState(
                depositItem = depositItem,
                isEntryValid = validateInput(depositItem),
                depositPeriodValue = depositItem.depositPeriodValue,
                isPayOutSelected = depositItem.isPayOutSelected
            )
    }


    suspend fun updateDepositItem() {
        //if (depositItemUiState.isPayOutSelected)

        if (validateInput()) {
            depositRepository.updateDeposit(
                depositItemUiState.depositItem.toDeposit()
            )
        }
    }

    private fun validateInput(uiState: DepositItem = depositItemUiState.depositItem): Boolean {
        return with(uiState) {
            title.isNotBlank() && depositAmount.isNotBlank() && depositPercent.isNotBlank()
                    && depositPeriodValue.isNotBlank()
        }
    }

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

private fun calculateDeposit(
    amount: Double,
    percentage: Double,
    period: Int,
    isPayOut: Boolean = true
): Double {
    return when(isPayOut) {
        true -> {
            calculateDepositWithPayoutAndYearPeriod(
                amount = amount,
                percentage = percentage,
                period = period
            )
        }
        false -> {
            calculateDepositWithAddToDepositAndYearPeriod(
                amount = amount,
                percentage = percentage,
                period = period
            )
        }
    }
}

/**
 * Function to calculate profitability with Pay out option and Year period
 */
private fun calculateDepositWithPayoutAndYearPeriod(
    amount: Double,
    percentage: Double,
    period: Int
): Double {
    return (amount * (percentage / 100) / 365 * (365 * period)).roundNumberToTwoDigits()
}

/**
 * Function to calculate profitability with Add To Deposit option and Year period
 */
private fun calculateDepositWithAddToDepositAndYearPeriod(
    amount: Double,
    percentage: Double,
    period: Int
): Double {
    val amountMonth: Int = period * 12

    var monthly = (amount * (percentage / 100) / (365 * period) * 30)
    val resultMonth = mutableListOf<Double>(monthly)
    var sumMonth = amount + monthly
    var month = 1


    while (month < amountMonth) {
        monthly = (sumMonth * (percentage / 100) / (365 * period) * 30)
        sumMonth += monthly
        resultMonth.add(monthly)
        month++
    }

    return (resultMonth.sum() * period).roundNumberToTwoDigits()
}

/**
 * Extension function to round number to two digits
 */
private fun Double.roundNumberToTwoDigits(): Double {
    return round((this) * 100.0) / 100.0
}

/**
 * UI state for an Deposit Item
 */
data class DepositItemUiState(
    val depositItem: DepositItem = DepositItem(),
    val isEntryValid: Boolean = false,
    val isPayOutSelected: Boolean = true,
    var depositPeriodValue: String = "1",
    var depositDateType: Int = 0
)

data class DepositItem(
    val idDeposit: Int = 0,
    val title: String = "",
    val depositAmount: String = "",
    val depositPercent: String = "",
    val lastCalculation: String = "",
    val depositPeriodValue: String = "",
    val isPayOutSelected: Boolean = true
)

/**
 * Extension function to convert [DepositItem] to [Deposit]
 */
fun DepositItem.toDeposit(): Deposit = Deposit(
    idDeposit = idDeposit,
    title = title,
    depositAmount = depositAmount.toDoubleOrNull() ?: 0.0,
    depositPercent = depositPercent.toDoubleOrNull() ?: 0.0,
    lastCalculation = calculateDeposit(
        amount = depositAmount.toDoubleOrNull() ?: 0.0,
        percentage = depositPercent.toDoubleOrNull() ?: 0.0,
        period = depositPeriodValue.toIntOrNull() ?: 1,
        isPayOut = isPayOutSelected
    )
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
fun Deposit.toDepositItemUiState(isEntryValid: Boolean = false): DepositItemUiState =
    DepositItemUiState(
        depositItem = this.toDepositItem(),
        isEntryValid = isEntryValid
    )