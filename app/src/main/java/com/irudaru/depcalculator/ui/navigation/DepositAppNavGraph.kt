package com.irudaru.depcalculator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.irudaru.depcalculator.ui.deposit.screens.DepositItemDestination
import com.irudaru.depcalculator.ui.deposit.screens.DepositItemEntryDestination
import com.irudaru.depcalculator.ui.deposit.screens.DepositItemEntryScreen
import com.irudaru.depcalculator.ui.deposit.screens.DepositItemScreen
import com.irudaru.depcalculator.ui.deposit.screens.DepositListDestination
import com.irudaru.depcalculator.ui.deposit.screens.DepositListScreen

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
                navigateToDepositItem = {
                    navController.navigate("${DepositItemDestination.route}/$it")
                },
                navigateToDepositItemUpdate = {
                    navController.navigate(DepositItemEntryDestination.route)
                }
            )
        }
        composable(route = DepositItemEntryDestination.route) {
            DepositItemEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = DepositItemDestination.routeWithArgs,
            arguments = listOf(navArgument(DepositItemDestination.depositItemIdArg) {
                type = NavType.IntType
            })
        ) {
            DepositItemScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}