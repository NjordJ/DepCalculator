package com.irudaru.depcalculator.ui.navigation

/**
 * Interface to describe navigation destination
 */
interface NavigationDestination {
    /**
     * Unique name to define path for composable
     */
    val route: String

    /**
     * String resource id that contains title
     */
    val titleRes: Int
}