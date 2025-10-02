@file:OptIn(ExperimentalMaterial3Api::class)
package ee.ut.cs.budgetly.ui.screens

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun HomeScreen(
    onMenuClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onPrevMonth: () -> Unit = {},
    onNextMonth: () -> Unit = {}
) {
    val cs = MaterialTheme.colorScheme
    val creamShape = RoundedCornerShape(24.dp)

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
                            Text("July 2025", style = MaterialTheme.typography.titleMedium)
                            IconButton(onClick = onNextMonth) { Icon(Icons.Default.ChevronRight, null) }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // Placeholders
                    Card(
                        modifier = Modifier.fillMaxWidth().height(220.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Pie placeholder") } }

                    Spacer(Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) { Text("List placeholder") } }
                }
            }
        }
    }
}