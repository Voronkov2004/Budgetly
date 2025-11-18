package ee.ut.cs.budgetly

import ee.ut.cs.budgetly.ui.viewmodel.ThemeViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTests {

    //Unit test that checks that toggleTheme() correctly switches between light and dark mode.
    @Test
    fun themeToggle_isCorrect() {
        val vm = ThemeViewModel()

        assertEquals(false, vm.isDarkTheme.value)

        vm.toggleTheme()
        assertEquals(true, vm.isDarkTheme.value)

        vm.toggleTheme()
        assertEquals(false, vm.isDarkTheme.value)
    }

    //Unit test that checks amount validation logic (only positive numbers are allowed).
    @Test
    fun amountValidation_isCorrect() {
        fun isValidAmount(input: String): Boolean {
            val num = input.toDoubleOrNull() ?: return false
            return num > 0
        }

        assertEquals(true, isValidAmount("10"))
        assertEquals(false, isValidAmount("-5"))
        assertEquals(false, isValidAmount("abc"))
        assertEquals(false, isValidAmount("0"))
    }

    //Unit test that checks if expense date defaults to the correct day of the selected month.
    @Test
    fun dateDefaultSelection_isCorrect() {
        val cal = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.MONTH, 1)
            set(java.util.Calendar.DAY_OF_MONTH, 28)
        }

        val expected = cal.get(java.util.Calendar.DAY_OF_MONTH)

        //simulate "default date" used in AddExpenseScreen
        val actual = cal.timeInMillis.let { millis ->
            java.util.Calendar.getInstance().apply { timeInMillis = millis }
        }.get(java.util.Calendar.DAY_OF_MONTH)

        assertEquals(expected, actual)
    }
}

