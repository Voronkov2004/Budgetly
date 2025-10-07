package ee.ut.cs.budgetly.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ee.ut.cs.budgetly.data.entity.Expense
import ee.ut.cs.budgetly.domain.model.CategoryExpenseSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert suspend fun insert(expense: Expense)
    @Query("SELECT * FROM expense ORDER BY date DESC") fun getAllByDateDesc(): Flow<List<Expense>>
    @Query("SELECT * FROM expense WHERE categoryId = :categoryId") fun getByCategory(categoryId: Int): Flow<List<Expense>>

    @Query("""
    SELECT c.name AS categoryName,
           COALESCE(SUM(e.amount), 0) AS totalAmount,
           c.color as color
    FROM category c
    LEFT JOIN expense e 
        ON c.id = e.categoryId 
       AND e.date BETWEEN :startTimestamp AND :endTimestamp
    GROUP BY c.id
""")
    fun getCategoryExpenseSummaryForMonth(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<CategoryExpenseSummary>>

    @Query("SELECT * FROM Expense")
    suspend fun getAll(): List<Expense>

    @Query("""
    SELECT COALESCE(SUM(amount), 0)
    FROM expense
    WHERE date BETWEEN :startTimestamp AND :endTimestamp
""")
    fun getTotalSpendingForMonth(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<Double>


}