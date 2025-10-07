@file:OptIn(ExperimentalMaterial3Api::class)
package ee.ut.cs.budgetly.ui.screens

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ee.ut.cs.budgetly.domain.model.CategoryExpenseSummary
import ee.ut.cs.budgetly.ui.component.CategoryPieChart
import java.util.Calendar
import java.util.Locale


@Composable
fun HomeScreen(
    onMenuClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onPrevMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {},
    categoryExpenses: List<CategoryExpenseSummary> = emptyList(),
    selectedMonth: Calendar

) {
    val cs = MaterialTheme.colorScheme
    val creamShape = RoundedCornerShape(24.dp)

    val total = remember(categoryExpenses) {
        categoryExpenses.sumOf { it.totalAmount }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(cs.background)   // OCHRE main background
    ) {
        val topBandShape = RoundedCornerShape(
            bottomStart = 20.dp,
            bottomEnd = 20.dp
        )
        val bottomBandShape = RoundedCornerShape(
            topStart = 20.dp,
            topEnd = 20.dp
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(200.dp)
                .clip(topBandShape)
                .background(cs.primary, topBandShape) // topBar

        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .clip(bottomBandShape)
                .fillMaxWidth()
                .height(120.dp)
                .background(cs.primary, bottomBandShape)    // bottomBar
        )


        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = cs.onPrimary,
                        actionIconContentColor = cs.onPrimary,
                        titleContentColor = cs.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = onMenuClick) { Icon(Icons.Default.Menu, null) }
                    },
                    title = {},
                    actions = {
                        IconButton(onClick = onAddClick) { Icon(Icons.Default.Add, null) }
                    }
                )
            }
        ) { inner ->
            // Foreground
            Surface(
                modifier = Modifier
                    .padding(inner)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                color = cs.surface,
                shape = creamShape,
                tonalElevation = 3.dp
            ) {
                Column(Modifier.fillMaxSize().padding(16.dp)) {

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

                    Spacer(Modifier.height(20.dp))

                    // Placeholders
                    Card(
                        modifier = Modifier.fillMaxWidth().height(220.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (categoryExpenses.isNotEmpty()) {
                                CategoryPieChart(categoryExpenses)
                            } else {
                                Text("No data", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) { LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {

                        item {
                            Row(
                                Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Category", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                                Text("Spent", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                            }
                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                color = Color.Gray.copy(alpha = 0.3f)
                            )
                        }


                        items(categoryExpenses) { item ->
                            Row(
                                Modifier.fillMaxWidth().padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(item.categoryName, style = MaterialTheme.typography.bodyLarge)
                                Text("${item.totalAmount} €", style = MaterialTheme.typography.bodyLarge)
                            }
                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                color = Color.Gray.copy(alpha = 0.3f)
                            )
                        }
                        item {
                            Spacer(Modifier.height(4.dp))
                            HorizontalDivider(
                                Modifier,
                                DividerDefaults.Thickness,
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 4.dp),
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
                    } }
                }
            }
        }
    }
}