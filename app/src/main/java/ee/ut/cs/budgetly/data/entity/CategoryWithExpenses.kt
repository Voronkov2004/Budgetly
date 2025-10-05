package ee.ut.cs.budgetly.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithExpenses(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val expenses: List<Expense>
)
