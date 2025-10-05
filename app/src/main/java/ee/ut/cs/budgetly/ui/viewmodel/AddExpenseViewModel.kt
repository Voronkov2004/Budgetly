package ee.ut.cs.budgetly.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ee.ut.cs.budgetly.data.database.AppDatabase
import ee.ut.cs.budgetly.data.entity.Category
import ee.ut.cs.budgetly.data.entity.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddExpenseViewModel(application: Application) : AndroidViewModel(application)  {
    private val expenseDao = AppDatabase.getInstance(application).expenseDao()
    private val categoryDao = AppDatabase.getInstance(application).categoryDao()

    val categories: StateFlow<List<Category>> = categoryDao.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addExpense(name: String, amount: Double, categoryId: Int, date: Long, note: String?) {

        viewModelScope.launch(Dispatchers.IO) {
            expenseDao.insert(
                Expense(
                    name = name,
                    amount = amount,
                    categoryId = categoryId,
                    date = date,
                    note = note
                )
            )
        }
    }
}