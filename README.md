# Budgetly

## Description

**Budgetly** is a personal finance tracking app built with **Kotlin**.  
It helps users manage their expenses, track budgets, and gain insights into their spending habits with ease.  
By automating routine tasks and giving users a clear overview of their finances, **Budgetly** saves time, reduces stress, and makes everyday money management effortless, helping users focus more on living and less on bookkeeping.    

### Planned features 

1. Add an expense made.
2. Set metadata of an expense: sum of money, date, name of product/service, and category.
3. Category selection from a default category list when adding a new expense.
4. Custom expense category creation.
5. Automatic category detection based on product/service name (if not set manually) using AI.
6. Automatic current date selection upon adding new expense (if not set manually)
7. Edit expense details (date, sum, category, name).
8. Expense history view by month with ability to scroll through months.
9. Pie chart of expenses by category for quick overview.
10. Sort expenses by date or category.
11. Search expenses by name.
12. Set a monthly budget for an expense category.
13. Track remaining balance for each budgeted category.
14. Notifications when a category budget reaches 50% or is fully spent.

**Additional features for future developement**  
13. Add and track income sources (cash, credit card, savings, etc.).  
14. Add income amount to each income source.  
15. Link each expense to the income source it was paid from.  
16. Track remaining money in each income source.  
17. Automatic expense entry from scanned receipts using ChatGPT.  

### Design mockup

<img width="412" height="917" alt="Untitled" src="https://github.com/user-attachments/assets/ff81c06b-4e59-4db6-bf65-476ccd3e63b9" />
<img width="412" height="917" alt="Untitled (1)" src="https://github.com/user-attachments/assets/6c563f44-81c9-479b-a564-70b6e65caf7d" />
<img width="412" height="917" alt="Untitled (2)" src="https://github.com/user-attachments/assets/f666ea77-86a6-449a-a300-9c1b7060ad5c" />


### Tools & Frameworks
- **Android Studio** – Primary IDE for development
- **Kotlin** – Main programming language
- **Gradle** – Build automation and dependency management


## Running the Test Application
To launch and test the **Budgetly** application locally, follow these steps:

1. **Clone the repository**  
   - Open a terminal and execute 'git clone https://github.com/Voronkov2004/Budgetly.git'

2. **Open the project in Android Studio**
    - Select **Open an existing project** and navigate to the cloned repository folder.

3. **Sync Gradle**
    - Android Studio will usually perform this automatically.
    - If required, click **File > Sync Project with Gradle Files**.

4. **Run the application**
    - Click the **Run** button in the toolbar.
    - Select either a connected Android device or an emulator to launch the app.

5. **Verify functionality**
    - **Home Screen**: Displays current expenses and categories.
    - **Add Expense Screen**: Use the + button to create a new expense entry.
    - **About Screen**: Navigate to view the user profile, including the profile picture, username, and total spendings.


## Collaborators and roles
1. Anton Voronkov – Lead Manager & Developer, coordinates tasks and contributes to coding features.
2. Jessenia Tsenkman – Designer & Developer, focuses on UI/UX and contributes to coding features.
3. Milena Petrova – Developer & Presenter, contributes to coding features and provides design support, leads presentations.
4. Christine Kaldoja – Developer, contributes to coding features and provides design support.
5. Timur Sirazitdinov – Developer, contributes to coding features.

All members participate in research and editing to maintain documentation and project quality.


## Contributing

<img width="1000" height="310" alt="image" src="https://github.com/user-attachments/assets/48528008-cdf0-4077-8c5b-f1347ef8aed3"/><br><br>
At this project, we follow the GitHub Flow methodology for managing our software development process.

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
