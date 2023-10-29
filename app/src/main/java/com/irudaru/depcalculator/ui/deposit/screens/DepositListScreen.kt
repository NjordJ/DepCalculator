package com.irudaru.depcalculator.ui.deposit.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.ui.DepositAppTopAppBar
import com.irudaru.depcalculator.ui.deposit.viewmodels.DepositListViewModel
import com.irudaru.depcalculator.ui.navigation.NavigationDestination
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme
import org.koin.androidx.compose.koinViewModel

/**
 * Destination for [DepositListScreen]
 */
object DepositListDestination : NavigationDestination {
    override val route = "deposit_list"
    override val titleRes = R.string.title_appBar_depositListScreen
}

/**
 * Entry route for [DepositListScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositListScreen(
    viewModel: DepositListViewModel = koinViewModel(),
    navigateToDepositItem: (Int) -> Unit,
    navigateToDepositItemUpdate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val depositListUiState by viewModel.depositListUiState.collectAsState()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DepositAppTopAppBar(
                title = stringResource(DepositListDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToDepositItemUpdate() },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.addDeposit_fab_depositListScreen)
                )
            }
        }
    ) { innerPadding ->
        DepositListBody(
            depositList = depositListUiState.depositList,
            onDepositItemClick = navigateToDepositItem,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            //.verticalScroll(rememberScrollState()) TODO: Error infinity scroll height
        )
    }
}

@Composable
private fun DepositListBody(
    depositList: List<Deposit>,
    onDepositItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (depositList.isEmpty()) {
        Text(
            text = stringResource(id = R.string.noDeposit_text_depositListScreen),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize()
                .wrapContentSize()
        )
    } else {
        DepositList(
            depositList = depositList,
            onDepositItemClick = { onDepositItemClick(it.idDeposit) },
            modifier = modifier
        )
    }
}

@Composable
private fun DepositList(
    depositList: List<Deposit>,
    onDepositItemClick: (Deposit) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = depositList, key = { it.idDeposit }) { deposit ->
            DepositCard(deposit = deposit,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onDepositItemClick(deposit) })
        }
    }
}

@Composable
private fun DepositCard(deposit: Deposit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Column(modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp, start = 10.dp)
            ) {
                Text(
                    text = deposit.title,
                    modifier = Modifier
                        .weight(1f)
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_percent),
                    contentDescription = stringResource(id = R.string.contributionPercent_textField_depositItemScreen),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = deposit.depositPercent.toString(),
                    modifier = Modifier.wrapContentWidth(Alignment.End)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = stringResource(id = R.string.depositMoney_textField_depositItemScreen),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(text = deposit.depositAmount.toString())
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DepositListScreenPreview() {
    DepCalculatorTheme {
        DepositList(
            depositList = listOf(
                Deposit(1, "Valuable", 5000.0, 7.0, 5350.0),
                Deposit(2, "Valuable", 5000.0, 7.0, 5350.0),
                Deposit(3, "Valuable", 5000.0, 7.0, 5350.0)
            ),
            onDepositItemClick = { }
        )
    }
}