package ee.ut.cs.budgetly

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.*
import org.junit.Rule
import org.junit.Test

class UITests {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    //UI test that checks the theme toggle button exists on Home screen.
    @Test
    fun themeToggle_exists() {
        rule.onNodeWithContentDescription("Toggle theme").assertExists()
    }

    //UI test that checks the Home screen shows the Category/Spent list headers.
    @Test
    fun homeScreen_headersExist() {
        rule.onNodeWithText("Category").assertExists()
        rule.onNodeWithText("Spent").assertExists()
    }

}
