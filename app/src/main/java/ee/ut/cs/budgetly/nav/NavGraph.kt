package ee.ut.cs.budgetly.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ee.ut.cs.budgetly.ui.screens.HomeScreen


@Composable
fun BudgetlyNavGraph() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "home") {
        composable("home") { HomeScreen(
            onAddClick = { nav.navigate("add") }
        ) }
        // composable("add") { AddExpenseScreen() }
        // composable("profile") { ProfileScreen() }
    }
}