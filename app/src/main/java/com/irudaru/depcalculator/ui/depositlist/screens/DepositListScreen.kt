package com.irudaru.depcalculator.ui.depositlist.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme

@Composable
fun DepositListScreen(modifier: Modifier = Modifier){
    LazyColumn(
        modifier = Modifier
            .padding(4.dp)
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
        DepositListScreen()
    }
}