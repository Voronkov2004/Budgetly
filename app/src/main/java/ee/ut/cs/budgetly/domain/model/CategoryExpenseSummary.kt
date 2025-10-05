package ee.ut.cs.budgetly.domain.model

data class CategoryExpenseSummary(
    val categoryName: String,
    val totalAmount: Double,
    val color: String?
)
