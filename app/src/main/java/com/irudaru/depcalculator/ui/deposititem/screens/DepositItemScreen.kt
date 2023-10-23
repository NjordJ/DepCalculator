package com.irudaru.depcalculator.ui.deposititem.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.ui.navigation.NavigationDestination
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme

/**
 * Destination for [DepositItemScreen]
 */
object DepositItemDestination: NavigationDestination {
    override val route = "deposit_item"
    override val titleRes = R.string.app_name
}

@Composable
fun DepositItemScreen() {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DepositItemContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DepositItemContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            Text(
                text = "Deposit name",
                modifier = Modifier
                    .weight(1f)
            )
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit deposit name"
                )
            }
        }
        TextField(
            value = "",
            label = { Text(text = "Amount of money") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = ""
                )
            },
            onValueChange = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
        TextField(
            value = "",
            label = { Text(text = "Contribution percent") },
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
            Text(text = "Calculate")
        }
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