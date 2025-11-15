package ee.ut.cs.budgetly.nav

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
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
import ee.ut.cs.budgetly.ui.viewmodel.ThemeViewModel


@Composable
fun BudgetlyNavGraph() {
    val nav = rememberNavController()
    val themeViewModel: ThemeViewModel = viewModel()
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    NavHost(nav, startDestination = "home") {
        composable("home") {

            val viewModel: HomeViewModel = viewModel()

            val categoryExpenses by viewModel.categoryExpenses.collectAsState()

            val selectedMonth by viewModel.selectedMonth.collectAsState()

            val isLoading by viewModel.isLoading

            HomeScreen(
                selectedMonth = selectedMonth,
                categoryExpenses = categoryExpenses,
                isLoading = isLoading,
                onPrevMonth = { viewModel.prevMonth() },
                onNextMonth = { viewModel.nextMonth() },
                onAddClick = { nav.navigate("add") },
                onMenuClick = { nav.navigate("about") },
                onToggleTheme = { themeViewModel.toggleTheme() },
                isDarkMode = isDarkTheme
            )
        }
        composable("add") { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                nav.getBackStackEntry("home")
            }
            val homeVm: HomeViewModel = viewModel(parentEntry)
            val selectedMonth by homeVm.selectedMonth.collectAsState()

            val addVm: AddExpenseViewModel = viewModel(backStackEntry)
            val categories by addVm.categories.collectAsState()

            AddExpenseScreen(
                viewModel = addVm,
                categoryList = categories,
                selectedMonth = selectedMonth,
                onSave = { name, amount, categoryId, date, note ->
                    addVm.addExpense(name, amount, categoryId, date, note)
                    nav.popBackStack()
                },
                onCancel = { nav.popBackStack() },
                isDarkMode = isDarkTheme
            )
        }
        composable("about") {
            AboutScreen(
                onBackClick = { nav.popBackStack() },
                onToggleTheme = { themeViewModel.toggleTheme() },
                isDarkMode = isDarkTheme
            )
        }
    }
}
