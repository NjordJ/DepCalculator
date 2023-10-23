package com.irudaru.depcalculator.ui.depositlist.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.irudaru.depcalculator.ui.DepositAppTopAppBar
import com.irudaru.depcalculator.ui.navigation.NavigationDestination
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme

/**
 * Destination for [DepositListScreen]
 */
object DepositListDestination: NavigationDestination {
    override val route = "deposit_list"
    override val titleRes = R.string.app_name
}

/**
 * Entry route for Deposit List Screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositListScreen(modifier: Modifier = Modifier){
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
                    contentDescription = stringResource(id = R.string.fab_add_deposit_item)
                )
            }
        }
    ) { innerPadding ->
        DepositListBody(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun DepositListBody(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(5) { index ->
            DepositCard(
                index = index,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun DepositCard(index: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        DepositCardContent(
            index = index,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun DepositCardContent(index: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = "Deposit $index",
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "5%",
                modifier = Modifier.wrapContentWidth(Alignment.End)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(text = "10000$")
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
        DepositListBody()
    }
}