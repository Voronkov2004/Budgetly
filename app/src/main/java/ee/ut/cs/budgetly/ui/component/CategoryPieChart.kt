package ee.ut.cs.budgetly.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import ee.ut.cs.budgetly.domain.model.CategoryExpenseSummary

@Composable
fun CategoryPieChart(categoryExpenses: List<CategoryExpenseSummary>) {
    val total = categoryExpenses.sumOf { it.totalAmount }
    if (total == 0.0) return

    Canvas(modifier = Modifier.size(180.dp)) {
        var startAngle = -90f

        categoryExpenses.forEach { item ->
            val sweepAngle = (item.totalAmount / total * 360f).toFloat()
            val color = try {
                Color(
                    (item.color ?: "#CCCCCC").toColorInt()
                )
            } catch (e: IllegalArgumentException) {
                Color.Gray
            }

            drawArc(
                color = color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true
            )
            startAngle += sweepAngle
        }
    }
}

