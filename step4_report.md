# Testing Strategy for Budgetly App

The testing strategy for Budgetly focuses on **ensuring core functionality and user interface elements work as expected**. It combines **unit tests** for logic verification and **UI tests** for validating screen components.

## 1. Unit Testing
- **Examples:**
  - `themeToggle_isCorrect()`: Confirms that toggling between light and dark themes works as intended.
  - `amountValidation_isCorrect()`: Ensures that only valid positive numbers are accepted as amounts.
  - `dateDefaultSelection_isCorrect()`: Checks that the default date selection logic produces the expected day of the month.
- **Approach:** Isolate individual functions or ViewModels and test their behavior under different inputs using assertions.

## 2. UI Testing
- **Examples:**
  - `themeToggle_exists()`: Checks that the theme toggle button is visible on the Home screen.
  - `homeScreen_headersExist()`: Verifies that the `"Category"` and `"Spent"` headers are displayed.
- **Approach:** Use Jetpack Compose testing framework to locate elements by **text or content description** and assert their existence.
