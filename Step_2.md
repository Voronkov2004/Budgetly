# Budgetly — Step Two Report

## Description
**Budgetly** is a personal finance tracking app built with **Kotlin**.  
In **Step Two**, we added three core screens, integrated a **Room** database for local storage, and implemented basic navigation. Users can add new expenses and view them on **Home**, while the **About** screen shows profile info and total spending.

---

## Implemented in Step Two

1. **Screens**
    - **Home** — Lists saved expenses.
    - **Add Expense** — Form to create a new expense.
    - **About** — Displays profile picture, username, and **total spending**.

2. **Data & Storage**
    - Integrated **Room** for local persistence.
    - Each **Expense** record includes:
        - **Amount** (money spent)
        - **Date** (date of the expense)
        - **Name** (product/service)
        - **Category**
    - Stored the set of **all categories**.
    - Established the **relationship** between categories and expenses (each expense references a category).

3. **Navigation**
    - Basic navigation between **Home**, **Add Expense**, and **About**:
        - Add a new expense → return to **Home** to see it listed.
        - View profile and total spending on **About**.

---

## What Went Well
- Solid planning and clear task breakdown.
- Strong teamwork and communication.
- Smooth **GitHub Flow** for contributions.

---

## Issues Encountered
- One **minor merge conflict**, resolved quickly with no side effects.

---

## Tools & Frameworks
- **Android Studio** – Primary IDE
- **Kotlin** – Main programming language
- **Gradle** – Build and dependency management
- **Room** – Local database persistence

---

## Summary
Step Two delivered the core UI screens, local data persistence via Room, and the navigation to tie them together. Users can add expenses, see them on **Home**, and view profile details and **total spending** on **About**.
