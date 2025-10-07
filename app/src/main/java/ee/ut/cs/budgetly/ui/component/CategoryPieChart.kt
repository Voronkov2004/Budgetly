package ee.ut.cs.budgetly.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import ee.ut.cs.budgetly.domain.model.CategoryExpenseSummary
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun CategoryPieChart(
    categoryExpenses: List<CategoryExpenseSummary>,
    modifier: Modifier = Modifier.size(180.dp),
    separatorColor: Color = Color.Black,
    separatorWidth: Dp = 1.dp
) {
    val total = categoryExpenses.sumOf { it.totalAmount }
    if (total == 0.0) return

    Canvas(modifier = modifier) {
        var startAngle = -90f
        val boundaries = mutableListOf<Float>()

        categoryExpenses.forEach { item ->
            val sweepAngle = (item.totalAmount / total * 360f).toFloat()
            if (sweepAngle <= 0f) return@forEach

            boundaries += startAngle

            val color = try {
                Color((item.color ?: "#CCCCCC").trim().toColorInt())
            } catch (_: IllegalArgumentException) {
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

        if (boundaries.size > 1) {
            val strokePx = separatorWidth.toPx()
            val radius = min(size.width, size.height) / 2f - strokePx / 2f
            val center = this.center

            boundaries.forEach { angleDeg ->
                val a = Math.toRadians(angleDeg.toDouble())
                val end = Offset(
                    x = center.x + radius * cos(a).toFloat(),
                    y = center.y + radius * sin(a).toFloat()
                )
                drawLine(
                    color = separatorColor.copy(alpha = 0.6f),
                    start = center,
                    end = end,
                    strokeWidth = strokePx
                )
            }
        }
    }
}
