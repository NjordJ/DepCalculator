package com.irudaru.depcalculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.irudaru.depcalculator.ui.deposititem.screens.DepositItemDestination
import com.irudaru.depcalculator.ui.deposititem.screens.DepositItemScreen
import com.irudaru.depcalculator.ui.depositlist.screens.DepositListDestination
import com.irudaru.depcalculator.ui.depositlist.screens.DepositListScreen

/**
 * Provides Navigation graph for the application
 */
@Composable
fun DepositAppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DepositListDestination.route,
        modifier = modifier
    ) {
        composable(route = DepositListDestination.route) {
            DepositListScreen(
                navigateToDepositItem = { navController.navigate(DepositItemDestination.route) },
                navigateToDepositItemUpdate = {
                    navController.navigate("")
                }
            )
        }
        composable(route = DepositItemDestination.route) {
            DepositItemScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}