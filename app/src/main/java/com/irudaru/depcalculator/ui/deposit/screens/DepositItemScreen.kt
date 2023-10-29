package com.irudaru.depcalculator.ui.deposit.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.ui.DepositAppTopAppBar
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItem
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItemUiState
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositItemViewModel
import com.irudaru.depcalculator.ui.deposit.viewmodels.toDepositItem
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
            onButtonClick = {
                coroutineScope.launch {
                    viewModel.updateDepositItem()
                    navigateBack()
                }
            },
            //onDepositItemValueChange = viewModel::updateUiState,
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
    onButtonClick: () -> Unit,
    onDepositItemValueChange: (DepositItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DepositItemContent(
            depositItem = depositItemUiState.depositItem,
            onValueChange = onDepositItemValueChange
        )

        Button(
            onClick = onButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(id = buttonText))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositItemContent(
    depositItem: DepositItem,
    onValueChange: (DepositItem) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = depositItem.title,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        )
    }
    TextField(
        value = depositItem.title,
        label = { Text(text = stringResource(id = R.string.title_textField_depositItemScreen)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_title),
                contentDescription = stringResource(id = R.string.title_textField_depositItemScreen)
            )
        },
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
        onValueChange = { onValueChange(depositItem.copy(depositPercent = it)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
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
                    7.0
                ).toDepositItem()
            ),
            buttonText = R.string.calculate_button_depositItemScreen,
            onButtonClick = {},
            onDepositItemValueChange = {}
        )
    }
}