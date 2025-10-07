package ee.ut.cs.budgetly.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ee.ut.cs.budgetly.ui.screens.AddExpenseScreen
import ee.ut.cs.budgetly.ui.screens.HomeScreen
import ee.ut.cs.budgetly.ui.screens.AboutScreen
import ee.ut.cs.budgetly.ui.viewmodel.AddExpenseViewModel
import ee.ut.cs.budgetly.ui.viewmodel.HomeViewModel


@Composable
fun BudgetlyNavGraph() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "home") {
        composable("home") {

            val viewModel: HomeViewModel = viewModel()

            val categoryExpenses by viewModel.categoryExpenses.collectAsState()

            val selectedMonth by viewModel.selectedMonth.collectAsState()
            HomeScreen(
                selectedMonth = selectedMonth,
                categoryExpenses = categoryExpenses,
                onPrevMonth = { viewModel.prevMonth() },
                onNextMonth = { viewModel.nextMonth() },
                onAddClick = { nav.navigate("add") },
                onMenuClick = { nav.navigate("about") }
            )
        }
        composable("add") { backStackEntry ->
            // Share HomeViewModel from the "home" destination
            val parentEntry = remember(backStackEntry) {
                nav.getBackStackEntry("home")
            }
            val homeVm: HomeViewModel = viewModel(parentEntry)
            val selectedMonth by homeVm.selectedMonth.collectAsState()

            val addVm: AddExpenseViewModel = viewModel(backStackEntry)
            val categories by addVm.categories.collectAsState()

            AddExpenseScreen(
                categoryList = categories,
                selectedMonth = selectedMonth,
                onSave = { name, amount, categoryId, date, note ->
                    addVm.addExpense(name, amount, categoryId, date, note)
                    nav.popBackStack()
                },
                onCancel = { nav.popBackStack() }
            )
        }
        composable("about") {
            AboutScreen(
                onBackClick = { nav.popBackStack() }
            )
        }
    }
}
