package com.irudaru.depcalculator.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.irudaru.depcalculator.R
import com.irudaru.depcalculator.ui.navigation.DepositAppNavHost

/**
 * Top level composable that shows screens for rest of the app
 */
@Composable
fun DepositApp(navController: NavHostController = rememberNavController()) {
    DepositAppNavHost(navController = navController)
}

/**
 * App bar to display title and buttons
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DepositAppTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    trailingButtons: @Composable RowScope.() -> Unit = {},
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(title) },
        scrollBehavior = scrollBehavior,
        actions = trailingButtons,
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.backButton_appBar)
                    )
                }
            }
        }
    )
}