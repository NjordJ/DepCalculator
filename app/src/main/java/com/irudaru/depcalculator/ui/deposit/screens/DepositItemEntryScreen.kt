package com.irudaru.depcalculator.ui.deposit.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.ui.DepositAppTopAppBar
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItemEntryViewModel
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItemUiState
import com.irudaru.depcalculator.ui.deposit.viewmodels.toDepositItem
import com.irudaru.depcalculator.ui.navigation.NavigationDestination
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * Destination for [DepositItemEntryScreen]
 */
object DepositItemEntryDestination : NavigationDestination {
    override val route = "deposit_item"
    override val titleRes = R.string.title_appBar_depositItemEntryScreen
}

/**
 * Entry route for [DepositItemEntryScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositItemEntryScreen(
    viewModel: DepositItemEntryViewModel = koinViewModel(),
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DepositAppTopAppBar(
                title = stringResource(DepositItemEntryDestination.titleRes),
                navigateUp = onNavigateUp,
                canNavigateBack = canNavigateBack,
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        DepositItemBody(
            depositItemUiState = viewModel.depositItemUiState,
            buttonText = R.string.save_button_depositItemScreen,
            onButtonClick = {
                coroutineScope.launch {
                    viewModel.saveDepositItem()
                    navigateBack()
                }
            },
            onDepositItemValueChange = viewModel::updateUiState,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DepositItemEntryScreenPreview() {
    DepCalculatorTheme {
        DepositItemBody(
            depositItemUiState = DepositItemUiState(
                Deposit(
                    1,
                    "Valuable",
                    5000.0,
                    7.0,
                    5350.0
                ).toDepositItem()
            ),
            buttonText = R.string.calculate_button_depositItemScreen,
            onButtonClick = {},
            onDepositItemValueChange = {}
        )
    }
}