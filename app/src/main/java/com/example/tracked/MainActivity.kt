package com.example.tracked

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudyHabitTrackerApp()
            }
        }
    }
}

@Composable
fun StudyHabitTrackerApp() {
    val navController = rememberNavController()
    var showAddDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, onFabClick = { showAddDialog = true }) },
        // Removed floatingActionButton since it's now in the bottom bar
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("study") { StudyScreen() }
            composable("progress") { ProgressScreen() }
            composable("profile") { ProfileScreen() }
        }

        if (showAddDialog) {
            AddOptionsDialog(
                onDismiss = { showAddDialog = false },
                onOptionSelected = { option ->
                    Toast.makeText(context, "Selected: $option", Toast.LENGTH_SHORT).show()
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, onFabClick: () -> Unit) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Custom bottom bar with FAB integrated as a central item
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)  // Height for the bar
    ) {
        // Background for the bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxSize()
        ) {}

        // Row for navigation items and central FAB
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Left items
            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = { navController.navigate("home") },
                icon = { Text("🏠") },
                label = { Text("Home") }
            )
            NavigationBarItem(
                selected = currentRoute == "study",
                onClick = { navController.navigate("study") },
                icon = { Text("📚") },
                label = { Text("Study") }
            )

            // Central FAB as a navigation item
            Box(
                modifier = Modifier
                    .size(56.dp)  // Standard FAB size
                    .offset(y = (-12).dp),  // Slight offset to make it prominent
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = onFabClick,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("➕")
                }
            }

            // Right items
            NavigationBarItem(
                selected = currentRoute == "progress",
                onClick = { navController.navigate("progress") },
                icon = { Text("📈") },
                label = { Text("Progress") }
            )
            NavigationBarItem(
                selected = currentRoute == "profile",
                onClick = { navController.navigate("profile") },
                icon = { Text("👤") },
                label = { Text("Profile") }
            )
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Study Progress",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Top 4 Bento Cards
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { BentoCard(title = "Today’s Tasks", data = "5", unit = "tasks") }
            item { BentoCard(title = "Assignments", data = "3", unit = "remaining") }
            item { BentoCard(title = "Study Sessions", data = "2", unit = "hours today") }
            item { BentoCard(title = "Overall Progress", data = "75%", unit = "complete") }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),  // Reduced height from 100.dp to 80.dp
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Modules",
                    style = MaterialTheme.typography.titleMedium
                )
                Button(onClick = { /* TODO: view modules */ }) {
                    Text("View")
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Motivational/Gauge Card (unchanged)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp, bottom = 8.dp, start = 12.dp, end = 12.dp),  // Reduced top padding from 2.dp to 0.dp
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left text column
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Doing a good job,",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Keep it up",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        textAlign = TextAlign.Start
                    )
                }
                // Right gauge
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    SemiCircularGauge(progress = 0.7f, modifier = Modifier.size(100.dp))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "14 hours",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Horizontal scrollable row of study session boxes
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                StudySessionCard(
                    title = "Photosynthesis",
                    subject = "Science",
                    titleFontSize = 20.sp
                )
            }
            item {
                StudySessionCard(
                    title = "Waste Management",
                    subject = "Science",
                    titleFontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun StudySessionCard(title: String, subject: String, titleFontSize: TextUnit = 24.sp) {
    Card(
        modifier = Modifier
            .width(280.dp)  // Fixed width for horizontal scrolling
            .height(140.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopStart),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Study Session",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = titleFontSize),  // Adjustable font size
                    textAlign = TextAlign.Start,
                    maxLines = Int.MAX_VALUE
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subject,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start
                )
            }
            IconButton(
                onClick = { /* Placeholder */ },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow"
                )
            }
        }
    }
}

@Composable
fun SemiCircularGauge(progress: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val strokeWidth = 10.dp.toPx()
        val radius = (size.minDimension / 2) - strokeWidth / 2
        val center = Offset(size.width / 2, size.height - strokeWidth / 2)

        drawArc(
            color = Color.LightGray,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(Color.Red, Color.Yellow, Color.Green),
            startX = center.x - radius,
            endX = center.x + radius
        )
        drawArc(
            brush = gradientBrush,
            startAngle = 180f,
            sweepAngle = 180f * progress,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        val pointerAngle = 180f + (180f * progress)
        val pointerLength = radius * 0.8f
        val pointerEnd = Offset(
            center.x + pointerLength * cos(Math.toRadians(pointerAngle.toDouble())).toFloat(),
            center.y + pointerLength * sin(Math.toRadians(pointerAngle.toDouble())).toFloat()
        )
        drawLine(
            color = Color.Black,
            start = center,
            end = pointerEnd,
            strokeWidth = 5.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun BentoCard(title: String, data: String, unit: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = unit,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BentoButton(icon: String, label: String, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddOptionsDialog(onDismiss: () -> Unit, onOptionSelected: (String) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "New Item",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Text("✕")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item { BentoButton(icon = "🗒️", label = "Task", onClick = { onOptionSelected("Task") }) }
                    item { BentoButton(icon = "📝", label = "Assignment", onClick = { onOptionSelected("Assignment") }) }
                    item { BentoButton(icon = "📚", label = "Study Session", onClick = { onOptionSelected("Study Session") }) }
                    item { BentoButton(icon = "⏰", label = "Reminder", onClick = { onOptionSelected("Reminder") }) }
                    item { BentoButton(icon = "🎯", label = "Goal", onClick = { onOptionSelected("Goal") }) }
                }
            }
        }
    }
}
