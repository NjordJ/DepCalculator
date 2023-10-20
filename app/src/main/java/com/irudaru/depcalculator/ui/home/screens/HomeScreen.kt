package com.irudaru.depcalculator.ui.home.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.irudaru.depcalculator.ui.depositlist.screens.DepositListScreen
import com.irudaru.depcalculator.ui.theme.DepCalculatorTheme

@Composable
fun HomeScreen() {
    DepositListScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {
    DepCalculatorTheme {
        DepositListScreen()
    }
}