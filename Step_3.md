## OpenAI API Integration

**Budgetly** integrates the **OpenAI API** to automatically suggest a category for each new expense based on its name.
This feature helps users add expenses faster by predicting the correct category automatically.

## How it works

When the user adds a new expense and presses “Auto Assign Category”,
the app sends the entered name (for example, “Pizza”) to the OpenAI GPT-4o-mini model.
The model replies with the most relevant category (for example, “Food”),
which is then automatically selected in the expense form.

## Code reference

- **AddExpenseViewModel.kt** – function fetchCategoryFromApi() sends the request to OpenAI API.

- **AddExpenseScreen.kt** – button “Auto Assign Category” triggers the category generation process.

## Setup instructions

1. To use this integration locally:

2. Go to your project root directory.

3. Create a file named **local.properties** (if it doesn’t already exist).

4. Add your OpenAI API key in the file:

"OPENAI_API_KEY=sk-your-api-key-here"


5. Sync your Gradle project in Android Studio.

## Dependencies

- **OkHttp** – for making HTTP requests

- **Gson** – for parsing JSON data

- **OpenAI API** – GPT-4o-mini model for category prediction

## Notes

- The API key must not be pushed to GitHub.

- Internet permission is required (android.permission.INTERNET is declared in the manifest).

- If the API fails or returns no result, the app displays an error message via Toast.

