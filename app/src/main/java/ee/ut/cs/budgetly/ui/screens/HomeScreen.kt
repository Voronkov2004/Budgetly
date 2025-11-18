@file:OptIn(ExperimentalMaterial3Api::class)
package ee.ut.cs.budgetly.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ee.ut.cs.budgetly.domain.model.CategoryExpenseSummary
import ee.ut.cs.budgetly.ui.component.CategoryPieChart
import java.util.*
import androidx.core.graphics.toColorInt



@Composable
fun HomeScreen(
    onMenuClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onPrevMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {},
    categoryExpenses: List<CategoryExpenseSummary> = emptyList(),
    selectedMonth: Calendar,
    isLoading: Boolean = false,
    onToggleTheme: () -> Unit = {},
    isDarkMode: Boolean = false,

) {
    val cs = MaterialTheme.colorScheme
    val total = remember(categoryExpenses) {
        categoryExpenses.sumOf { it.totalAmount }
    }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Budgetly", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Toggle Theme"
                        )
                    }
                    IconButton(onClick = onAddClick) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Expense"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = cs.primary,
                    titleContentColor = cs.onPrimary,
                    navigationIconContentColor = cs.onPrimary,
                    actionIconContentColor = cs.onPrimary
                )
            )
        },

        containerColor = cs.background
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .background(cs.background)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                color = cs.surface,
                shape = RoundedCornerShape(24.dp),
                tonalElevation = 3.dp
            ) {
                Column(Modifier.padding(16.dp)) {
                    // Month selector
                    Surface(
                        color = cs.primaryContainer,
                        contentColor = cs.onPrimaryContainer,
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = onPrevMonth) { Icon(Icons.Default.ChevronLeft, null) }
                            val monthText = remember(selectedMonth) {
                                val sdf = java.text.SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                                sdf.format(selectedMonth.time)
                            }
                            Text(monthText, style = MaterialTheme.typography.titleMedium)
                            IconButton(onClick = onNextMonth) { Icon(Icons.Default.ChevronRight, null) }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Pie chart area
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cs.surfaceVariant),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.animation.AnimatedVisibility(
                                visible = categoryExpenses.isNotEmpty(),
                                enter = EnterTransition.None,
                                exit = ExitTransition.None
                            ) {
                                CategoryPieChart(categoryExpenses)
                            }
                            if (categoryExpenses.isEmpty()) {
                                Text(
                                    text = "No data yet",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = cs.onSurfaceVariant
                                )
                            }
                        }
                    }



                    Spacer(Modifier.height(16.dp))

                    // Category list
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        LazyColumn(modifier = Modifier.padding(16.dp)) {
                            item {
                                Row(
                                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Category", style = MaterialTheme.typography.titleMedium)
                                    Text("Spent", style = MaterialTheme.typography.titleMedium)
                                }
                                HorizontalDivider(color = cs.outlineVariant)

                            }

                            items(categoryExpenses) { item ->
                                val swatchColor = remember(item.color) {
                                    val parsedInt: Int = item.color?.let { hex ->
                                        val normalized = when {
                                            hex.startsWith("#") -> hex
                                            hex.startsWith("0x", true) -> "#${hex.drop(2)}"
                                            else -> "#$hex"
                                        }
                                        runCatching { normalized.toColorInt() }.getOrElse { 0xFF9E9E9E.toInt() }
                                    } ?: 0xFF9E9E9E.toInt()
                                    Color(parsedInt)
                                }

                                Row(
                                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            Modifier
                                                .size(12.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                                .background(swatchColor)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(item.categoryName, style = MaterialTheme.typography.bodyLarge)
                                    }

                                    Text(
                                        String.format(Locale.getDefault(), "%,.2f €", item.totalAmount),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }

                                Divider(color = cs.outlineVariant)
                            }

                            item {
                                Spacer(Modifier.height(8.dp))
                                Divider(color = cs.outline)
                                Row(
                                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Total", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                    Text(
                                        String.format(Locale.getDefault(), "%,.2f €", total),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = cs.primary)
                }
            }
        }
    }
}