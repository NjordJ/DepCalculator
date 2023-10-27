package com.irudaru.depcalculator.ui.depositlist.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.data.deposit.DepositListDataSample
import com.irudaru.depcalculator.ui.DepositAppTopAppBar
import com.irudaru.depcalculator.ui.navigation.NavigationDestination
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme

/**
 * Destination for [DepositListScreen]
 */
object DepositListDestination: NavigationDestination {
    override val route = "deposit_list"
    override val titleRes = R.string.title_appBar_depositListScreen
}

/**
 * Entry route for [DepositListScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositListScreen(
    navigateToDepositItem: () -> Unit,
    navigateToDepositItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier
){
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
                onClick = { /*TODO: Add deposit button*/ },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.addDeposit_fab_depositItemScreen)
                )
            }
        }
    ) { innerPadding ->
        DepositListBody(
            depositList = DepositListDataSample.depositList,
            navigateToDepositItem = navigateToDepositItem,
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
    navigateToDepositItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(depositList.size) {index ->
            DepositCard(
                deposit = depositList[index],
                modifier = Modifier
                    .clickable(onClick = navigateToDepositItem)
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun DepositCard(deposit: Deposit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        DepositCardContent(
            deposit = deposit,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun DepositCardContent(deposit: Deposit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = deposit.title,
                modifier = Modifier
                    .weight(1f)
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
            Text(text = deposit.depositAmount.toString())
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
        DepositListBody(
            depositList = DepositListDataSample.depositList,
            navigateToDepositItem = {}
        )
    }
}