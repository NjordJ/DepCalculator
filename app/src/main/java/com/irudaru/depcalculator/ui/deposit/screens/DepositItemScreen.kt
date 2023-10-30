package com.irudaru.depcalculator.ui.deposit.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.ui.DepositAppTopAppBar
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItem
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItemUiState
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItemViewModel
import com.irudaru.depcalculator.ui.deposit.viewmodels.toDepositItem
import com.irudaru.depcalculator.ui.deposit.viewmodels.toDepositItemUiState
import com.irudaru.depcalculator.ui.navigation.NavigationDestination
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * Destination for [DepositItemScreen]
 */
object DepositItemDestination : NavigationDestination {
    override val route = "deposit_item"
    override val titleRes = R.string.title_appBar_depositItemScreen
    const val depositItemIdArg = "idDeposit"
    val routeWithArgs = "$route/{$depositItemIdArg}"
}

/**
 * Entry route for [DepositItemScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositItemScreen(
    viewModel: DepositItemViewModel = koinViewModel(),
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier
) {
    //val depositListUiState by viewModel.depositItemUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DepositAppTopAppBar(
                title = stringResource(DepositItemDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        DepositItemBody(
            depositItemUiState = viewModel.depositItemUiState, //depositListUiState.depositItem.toDeposit(),
            //depositItemUiState = depositListUiState,
            buttonText = R.string.calculate_button_depositItemScreen,
            deleteButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.deleteDepositItem()
                            navigateBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(id = R.string.delete_button_depositItemScreen))
                }
            },
            calculationHistory = {
                CalculationHistory(
                    depositItem = viewModel.depositItemUiState.depositItem
                )
            },
            extraDepositOptions = {
                ExtraDepositOptions(
                    depositItemUiState = viewModel.depositItemUiState,
                    onDatePeriodValueChange = viewModel::updateUiState,
                    onPayOutSelected = viewModel::updateUiState,
                    onAddToDepositSelected = viewModel::updateUiState
                )
            },
            onButtonClick = {
                coroutineScope.launch {
                    viewModel.updateDepositItem()
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

@Composable
fun DepositItemBody(
    depositItemUiState: DepositItemUiState,
    @StringRes buttonText: Int,
    deleteButton: @Composable () -> Unit = {},
    calculationHistory: @Composable () -> Unit = {},
    extraDepositOptions: @Composable () -> Unit = {},
    onButtonClick: () -> Unit,
    onDepositItemValueChange: (DepositItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DepositItemContent(
            depositItem = depositItemUiState.depositItem,
            onValueChange = onDepositItemValueChange,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        extraDepositOptions.invoke()
        Button(
            onClick = onButtonClick,
            enabled = depositItemUiState.isEntryValid,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = buttonText))
        }
        deleteButton.invoke()
        calculationHistory.invoke()
    }
}

@Composable
private fun ExtraDepositOptions(
    depositItemUiState: DepositItemUiState,
    onDatePeriodValueChange: (DepositItem) -> Unit = {},
    onPayOutSelected: (DepositItem) -> Unit = {},
    onAddToDepositSelected: (DepositItem) -> Unit = {},
) {
    DateRangeOptions(
        depositItemUiState = depositItemUiState,
        onValueChange = onDatePeriodValueChange
    )
    PercentageOptions(
        depositItemUiState = depositItemUiState,
        onPayOutSelected = onPayOutSelected,
        onAddToDepositSelected = onAddToDepositSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangeOptions(
    depositItemUiState: DepositItemUiState,
    onValueChange: (DepositItem) -> Unit,
) {
    var isDropdownMenuExpanded by remember { mutableStateOf(false) }
    var selectedMenuValue by remember { mutableIntStateOf(R.string.year_dropDownMenuItem_depositItemScreen) }

    Column(
        modifier = Modifier
            .padding(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                // TODO: Make date range selection, currently available only year
                onClick = { isDropdownMenuExpanded = true },
                enabled = false,
                modifier = Modifier
                    .alpha(0f)
            ) {
                Text(text = stringResource(id = R.string.selectPeriod_button_depositItemScreen))
            }
            Text(
                text = stringResource(selectedMenuValue),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
            TextField(
                value = depositItemUiState.depositPeriodValue,
                label = { Text(text = stringResource(id = R.string.depositPeriod_textField_depositItemScreen)) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_date),
                        contentDescription = stringResource(id = R.string.depositMoney_textField_depositItemScreen)
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    onValueChange(depositItemUiState.depositItem.copy(depositPeriodValue = it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        DropdownMenu(
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.day_dropDownMenuItem_depositItemScreen))
                },
                onClick = { selectedMenuValue = R.string.day_dropDownMenuItem_depositItemScreen }
            )
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.month_dropDownMenuItem_depositItemScreen))
                },
                onClick = { selectedMenuValue = R.string.month_dropDownMenuItem_depositItemScreen }
            )
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.year_dropDownMenuItem_depositItemScreen))
                },
                onClick = { selectedMenuValue = R.string.year_dropDownMenuItem_depositItemScreen }
            )
        }
    }
}

@Composable
private fun PercentageOptions(
    depositItemUiState: DepositItemUiState,
    onPayOutSelected: (DepositItem) -> Unit,
    onAddToDepositSelected: (DepositItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = depositItemUiState.isPayOutSelected,
                onCheckedChange = {
                    onPayOutSelected(
                        depositItemUiState.depositItem.copy(
                            isPayOutSelected = it
                        )
                    )
                }
            )
            Text(text = stringResource(id = R.string.payOut_radioButton_depositItemScreen))

            Checkbox(
                checked = !depositItemUiState.depositItem.isPayOutSelected,
//                onCheckedChange = {
//                    depositItemUiState.depositItem.copy(isPayOutSelected = !it)
//                },
                onCheckedChange = {
                    onAddToDepositSelected(
                        depositItemUiState.depositItem.copy(
                            isPayOutSelected = !it
                        )
                    )
                }
            )
            Text(text = stringResource(id = R.string.addToDeposit_radioButton_depositItemScreen))
        }
    }
}

@Composable
private fun CalculationHistory(
    depositItem: DepositItem,
) {
    Divider(
        color = MaterialTheme.colorScheme.primary,
        thickness = 2.dp,
        modifier = Modifier
            .padding(top = 8.dp)
            .height(2.dp)
            .fillMaxWidth()
    )
    Text(
        text = stringResource(id = R.string.calculationHistory_text_depositItemScreen),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
    )
    CalculationCard(
        depositItem = depositItem,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}

@Composable
private fun CalculationCard(
    depositItem: DepositItem,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.profitabilityForYear_text_depositItemScreen),
                style = MaterialTheme.typography.titleMedium
            )
            Row {
                Text(text = depositItem.lastCalculation)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositItemContent(
    depositItem: DepositItem,
    onValueChange: (DepositItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        TextField(
            value = depositItem.title,
            label = { Text(text = stringResource(id = R.string.title_textField_depositItemScreen)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_title),
                    contentDescription = stringResource(id = R.string.title_textField_depositItemScreen)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            onValueChange = { onValueChange(depositItem.copy(title = it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        TextField(
            value = depositItem.depositAmount,
            label = { Text(text = stringResource(id = R.string.depositMoney_textField_depositItemScreen)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = stringResource(id = R.string.depositMoney_textField_depositItemScreen)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            onValueChange = { onValueChange(depositItem.copy(depositAmount = it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        TextField(
            value = depositItem.depositPercent,
            label = { Text(text = stringResource(id = R.string.contributionPercent_textField_depositItemScreen)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_percent),
                    contentDescription = stringResource(id = R.string.contributionPercent_textField_depositItemScreen)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            onValueChange = { onValueChange(depositItem.copy(depositPercent = it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DepositItemScreenPreview() {
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
            deleteButton = {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(id = R.string.delete_button_depositItemScreen))
                }
            },
            calculationHistory = {
                CalculationHistory(
                    depositItem = Deposit(
                        1,
                        "Valuable",
                        5000.0,
                        7.0,
                        5350.0
                    ).toDepositItem()
                )
            },
            extraDepositOptions = {
                ExtraDepositOptions(
                    depositItemUiState = Deposit(
                        1,
                        "Valuable",
                        5000.0,
                        7.0,
                        5350.0
                    ).toDepositItemUiState()
                )
            },
            onButtonClick = {},
            onDepositItemValueChange = {}
        )
    }
}