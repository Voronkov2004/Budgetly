package ee.ut.cs.budgetly.ui.viewmodel


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ee.ut.cs.budgetly.data.dao.ExpenseDao
import ee.ut.cs.budgetly.data.database.AppDatabase
import ee.ut.cs.budgetly.domain.model.CategoryExpenseSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _selectedMonth = MutableStateFlow(Calendar.getInstance())
    val selectedMonth: StateFlow<Calendar> = _selectedMonth

    private val dao: ExpenseDao

    val categoryExpenses: StateFlow<List<CategoryExpenseSummary>>

    init {

        dao = AppDatabase.getInstance(application).expenseDao()

        categoryExpenses = _selectedMonth.flatMapLatest { cal ->
            val startCal = cal.clone() as Calendar
            startCal.set(Calendar.DAY_OF_MONTH, 1)
            startCal.set(Calendar.HOUR_OF_DAY, 0)
            startCal.set(Calendar.MINUTE, 0)
            startCal.set(Calendar.SECOND, 0)
            startCal.set(Calendar.MILLISECOND, 0)
            val startTimestamp = startCal.timeInMillis

            val endCal = cal.clone() as Calendar
            endCal.set(Calendar.DAY_OF_MONTH, endCal.getActualMaximum(Calendar.DAY_OF_MONTH))
            endCal.set(Calendar.HOUR_OF_DAY, 23)
            endCal.set(Calendar.MINUTE, 59)
            endCal.set(Calendar.SECOND, 59)
            endCal.set(Calendar.MILLISECOND, 999)
            val endTimestamp = endCal.timeInMillis

            dao.getCategoryExpenseSummaryForMonth(startTimestamp, endTimestamp)
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun prevMonth() {
        val newCal = _selectedMonth.value.clone() as Calendar
        newCal.add(Calendar.MONTH, -1)
        _selectedMonth.value = newCal
    }

    fun nextMonth() {
        val newCal = _selectedMonth.value.clone() as Calendar
        newCal.add(Calendar.MONTH, 1)
        _selectedMonth.value = newCal
    }
}




