package com.irudaru.depcalculator.ui.deposititem.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.data.deposit.Deposit
import com.irudaru.depcalculator.data.deposit.DepositItemDataSample
import com.irudaru.depcalculator.ui.DepositAppTopAppBar
import com.irudaru.depcalculator.ui.navigation.NavigationDestination
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme

/**
 * Destination for [DepositItemScreen]
 */
object DepositItemDestination: NavigationDestination {
    override val route = "deposit_item"
    override val titleRes = R.string.title_appBar_depositItemScreen
}
/**
 * Entry route for [DepositItemScreen]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositItemScreen(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DepositAppTopAppBar(
                title = stringResource(DepositItemDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        DepositItemBody(
            depositItem = DepositItemDataSample.depositItem,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun DepositItemBody(
    depositItem: Deposit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        DepositItemContent(depositItem)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepositItemContent(depositItem: Deposit) {
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
        value = depositItem.depositAmount.toString(),
        label = { Text(text = stringResource(id = R.string.depositMoney_textField_depositItemScreen)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_money),
                contentDescription = ""
            )
        },
        onValueChange = { /*TODO:*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
    TextField(
        value = depositItem.depositPercent.toString(),
        label = { Text(text = stringResource(id = R.string.contributionPercent_textField_depositItemScreen)) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_percent),
                contentDescription = ""
            )
        },
        onValueChange = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )

    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(text = stringResource(id = R.string.calculate_button_depositItemScreen))
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DepositItemScreenPreview() {
    DepCalculatorTheme {
        DepositItemScreen()
    }
}