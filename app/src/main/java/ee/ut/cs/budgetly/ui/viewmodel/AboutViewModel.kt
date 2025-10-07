package ee.ut.cs.budgetly.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ee.ut.cs.budgetly.data.database.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class AboutViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseDao = AppDatabase.getInstance(application).expenseDao()

    val totalSpendingThisMonth: StateFlow<Double> = flow {
        val (start, end) = currentMonthRange()
        emitAll(expenseDao.getTotalSpendingForMonth(start, end))
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    private fun currentMonthRange(): Pair<Long, Long> {
        val c = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        val start = c.timeInMillis
        c.add(Calendar.MONTH, 1)
        c.add(Calendar.MILLISECOND, -1)
        val end = c.timeInMillis
        return start to end
    }
}
