package com.example.tracked

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ProgressScreen() {
    // Simulate weekly study items (replace with real data from ViewModel/database)
    val weeklyStudyItems = listOf(
        StudyItem("Task", "Complete Math Homework", status = "Completed"),
        StudyItem("Assignment", "Submit Physics Report", subject = "Physics", status = "Completed"),
        StudyItem("Study Session", "Photosynthesis Review", subject = "Science", duration = "25 min", status = "In Progress"),
        StudyItem("Reminder", "Study Break at 5 PM", status = "Completed"),
        StudyItem("Goal", "Read 50 pages of Biology", subject = "Biology", status = "Completed"),
        StudyItem("Task", "Review Chemistry Notes", status = "Failed"),
        StudyItem("Assignment", "Prepare for History Exam", subject = "History", status = "In Progress"),
        StudyItem("Study Session", "Waste Management Lecture", subject = "Science", duration = "30 min", status = "Completed"),
        StudyItem("Reminder", "Call Study Group", status = "In Progress"),
        StudyItem("Goal", "Achieve 90% in Math Test", subject = "Math", status = "Failed")
    )

    // Calculate completion rate: Percentage of items with status "Completed"
    val totalItems = weeklyStudyItems.size
    val completedItems = weeklyStudyItems.count { it.status == "Completed" }
    val completionRate = if (totalItems > 0) (completedItems.toFloat() / totalItems.toFloat()) else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Weekly Study Report",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Weekly Progress Chart Card (Daily Breakdown)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Daily Study Time (This Week - Hours)",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Simple bar chart (placeholder daily hours)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val dailyData = listOf(2f, 4f, 3f, 5f, 6f, 4f, 7f)  // Placeholder hours for Mon-Sun
                    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                    dailyData.forEachIndexed { index, hours ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Canvas(modifier = Modifier.size(30.dp, 100.dp)) {
                                drawRect(
                                    color = Color.Blue,
                                    topLeft = Offset(0f, size.height - (hours / 8f * size.height)),  // Scale to max 8 hours
                                    size = Size(size.width, hours / 8f * size.height)
                                )
                            }
                            Text(text = days[index], style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stats Cards Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
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
                        text = "Total Study Time (This Week)",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "45h",  // Placeholder weekly total
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Card(
                modifier = Modifier
                    .weight(1f)
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
                        text = "Goals Achieved (This Week)",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "$completedItems",  // Dynamic count of completed items
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Completion Rate Gauge Card (With Table Layout, Average, and Button)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),  // Adjusted height for table layout
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 16.dp, end = 16.dp, bottom = 16.dp),  // Shorter left padding
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                // Calculate rates for each type (moved outside Column for accessibility)
                val taskItems = weeklyStudyItems.filter { it.type == "Task" }
                val assignmentItems = weeklyStudyItems.filter { it.type == "Assignment" }
                val goalItems = weeklyStudyItems.filter { it.type == "Goal" }
                val taskRate = if (taskItems.isNotEmpty()) taskItems.count { it.status == "Completed" }.toFloat() / taskItems.size else 0f
                val assignmentRate = if (assignmentItems.isNotEmpty()) assignmentItems.count { it.status == "Completed" }.toFloat() / assignmentItems.size else 0f
                val goalRate = if (goalItems.isNotEmpty()) goalItems.count { it.status == "Completed" }.toFloat() / goalItems.size else 0f
                val averageRate = (taskRate + assignmentRate + goalRate) / 3f  // Average of the three

                // Left side: Title and table layout
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Weekly Completion Rate",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Table layout: Headers
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("Task", style = MaterialTheme.typography.bodySmall)
                        Text("Assignment", style = MaterialTheme.typography.bodySmall)
                        Text("Goals", style = MaterialTheme.typography.bodySmall)
                    }
                    // Percentages (emphasized)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            "${(taskRate * 100).toInt()}%",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            "${(assignmentRate * 100).toInt()}%",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            "${(goalRate * 100).toInt()}%",
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    // Subtexts (normal and small)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("Finished", style = MaterialTheme.typography.bodySmall)
                        Text("Finished", style = MaterialTheme.typography.bodySmall)
                        Text("Achieved", style = MaterialTheme.typography.bodySmall)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Button for viewing all past progress reports
                    Button(
                        onClick = { /* TODO: Implement view all past progress reports */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("View Previous")
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))  // Gap

                // Right side: Gauge with average inside and progress label below
                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.padding(top = 16.dp),  // Add top space to gauge
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.size(100.dp)) {
                            drawArc(
                                color = Color.LightGray,
                                startAngle = 0f,
                                sweepAngle = 360f,
                                useCenter = false,
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 10.dp.toPx())
                            )
                            drawArc(
                                color = if (averageRate >= 0.7f) Color.Green else if (averageRate >= 0.5f) Color.Yellow else Color.Red,
                                startAngle = -90f,
                                sweepAngle = 360f * averageRate,
                                useCenter = false,
                                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 10.dp.toPx())
                            )
                        }
                        Text(
                            text = "${(averageRate * 100).toInt()}%",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Progress label below the gauge
                    Text(
                        text = "Progress",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Recent Achievements List (Weekly)
        Text(
            text = "Weekly Achievements",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().height(200.dp),  // Fixed height for scrollable list
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(weeklyStudyItems.filter { it.status == "Completed" }) { item ->  // Show only completed items
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "🏆",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${item.type}: ${item.title} - Completed!",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}
