package com.example.tracked

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableIntStateOf
import java.util.Locale

// Data class for study items (can be expanded later for more fields like date, priority, etc.)
data class StudyItem(
    val type: String,  // "Task", "Assignment", "Study Session", "Reminder", "Goal"
    val title: String,
    val subject: String? = null,  // Optional for some types
    val duration: String? = null,  // For Study Session
    val status: String = "In Progress"  // "Completed", "In Progress", "Failed"
)

@Composable
fun StudyScreen() {
    var timerRunning by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableIntStateOf(25 * 60) }  // 25 minutes in seconds (Pomodoro style)
    var totalStudyTime by remember { mutableIntStateOf(0) }  // In seconds, for today's total
    var selectedGroup by remember { mutableStateOf<String?>(null) }  // For dialog: which group is selected
    var selectedItem by remember { mutableStateOf<StudyItem?>(null) }  // For status selection

    // Timer logic (simple countdown)
    LaunchedEffect(timerRunning) {
        while (timerRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
            totalStudyTime++
        }
        if (timeLeft == 0) {
            timerRunning = false
            // Optional: Show a toast or notification when timer ends
        }
    }

    // Placeholder list of mixed items (replace with real data from ViewModel/database)
    var studyItems by remember {
        mutableStateOf(
            listOf(
                StudyItem("Task", "Complete Math Homework", status = "In Progress"),
                StudyItem("Assignment", "Submit Physics Report", subject = "Physics", status = "Completed"),
                StudyItem("Study Session", "Photosynthesis Review", subject = "Science", duration = "25 min", status = "In Progress"),
                StudyItem("Reminder", "Study Break at 5 PM", status = "In Progress"),
                StudyItem("Goal", "Read 50 pages of Biology", subject = "Biology", status = "Completed"),
                StudyItem("Task", "Review Chemistry Notes", status = "In Progress"),
                StudyItem("Assignment", "Prepare for History Exam", subject = "History", status = "In Progress"),
                StudyItem("Study Session", "Waste Management Lecture", subject = "Science", duration = "30 min", status = "Completed"),
                StudyItem("Reminder", "Call Study Group", status = "In Progress"),
                StudyItem("Goal", "Achieve 90% in Math Test", subject = "Math", status = "Failed")
            )
        )
    }

    // Group items by type for bento boxes
    val groupedItems = studyItems.groupBy { it.type }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)  // Removed bottom padding to eliminate white bar below bento boxes
            .verticalScroll(rememberScrollState()),  // Make the screen scrollable
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Study Sessions",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Quick stats card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Today's Sessions",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "3",  // Placeholder; replace with actual data
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Total Time",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${totalStudyTime / 60}m",  // Convert seconds to minutes
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Study timer card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
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
                    text = "Focus Timer",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = String.format(Locale.getDefault(), "%02d:%02d", timeLeft / 60, timeLeft % 60),
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(
                        onClick = {
                            timerRunning = !timerRunning
                            if (!timerRunning) timeLeft = 25 * 60  // Reset to 25 min
                        }
                    ) {
                        Text(if (timerRunning) "Pause" else "Start")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            timerRunning = false
                            timeLeft = 25 * 60
                        }
                    ) {
                        Text("Reset")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Custom bento layout
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // First row: Long box for Study Session
            StudySessionLongCard(
                count = groupedItems["Study Session"]?.size ?: 0,
                onClick = { selectedGroup = "Study Session" }
            )

            // Second row: Task and Assignment
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GroupBentoCard(
                    title = "Tasks",
                    count = groupedItems["Task"]?.size ?: 0,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedGroup = "Task" }
                )
                GroupBentoCard(
                    title = "Assignments",
                    count = groupedItems["Assignment"]?.size ?: 0,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedGroup = "Assignment" }
                )
            }

            // Third row: Reminders and Goals
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GroupBentoCard(
                    title = "Reminders",
                    count = groupedItems["Reminder"]?.size ?: 0,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedGroup = "Reminder" }
                )
                GroupBentoCard(
                    title = "Goals",
                    count = groupedItems["Goal"]?.size ?: 0,
                    modifier = Modifier.weight(1f),
                    onClick = { selectedGroup = "Goal" }
                )
            }
        }
    }

    // Dialog for selected group
    selectedGroup?.let { group ->
        GroupItemsDialog(
            group = group,
            items = groupedItems[group] ?: emptyList(),
            onItemClick = { item -> selectedItem = item },
            onDismiss = { selectedGroup = null }
        )
    }

    // Dialog for status selection
    selectedItem?.let { item ->
        StatusSelectionDialog(
            item = item,
            onStatusSelected = { newStatus ->
                studyItems = studyItems.map { if (it == item) it.copy(status = newStatus) else it }
                selectedItem = null
            },
            onDismiss = { selectedItem = null }
        )
    }
}

@Composable
fun StudySessionLongCard(count: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
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
                text = "Study Session",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start
            )
            Button(onClick = onClick) {
                Text("Enter")
            }
        }
    }
}

@Composable
fun GroupBentoCard(title: String, count: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier
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
                text = "$count",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "items",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun GroupItemsDialog(group: String, items: List<StudyItem>, onItemClick: (StudyItem) -> Unit, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 300.dp, max = 600.dp)  // Dynamic height: min 300dp, max 600dp for scrollability
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "$group Items",
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

                if (items.isEmpty()) {
                    Text(
                        text = "No $group items yet.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),  // Fill available height and scroll if needed
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items) { item ->
                            StudyItemComposable(item = item, onClick = { onItemClick(item) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusSelectionDialog(item: StudyItem, onStatusSelected: (String) -> Unit, onDismiss: () -> Unit) {
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
                Text(
                    text = "Update Status for: ${item.title}",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { onStatusSelected("Completed"); onDismiss() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Completed")
                    }
                    Button(
                        onClick = { onStatusSelected("In Progress"); onDismiss() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("In Progress")
                    }
                    Button(
                        onClick = { onStatusSelected("Failed"); onDismiss() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Failed")
                    }
                }
            }
        }
    }
}

@Composable
fun StudySessionItem(title: String, subject: String, duration: String, completed: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$subject • $duration",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = if (completed) "✓ Completed" else "In Progress",
                style = MaterialTheme.typography.bodyMedium,
                color = if (completed) Color.Green else Color(0xFFFFA500)  // Orange as fallback if Color.Orange unresolved
            )
        }
    }
}

@Composable
fun StudyItemComposable(item: StudyItem, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start  // Align to start for left-side status
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = when (item.status) {
                            "Completed" -> "✓ Completed"
                            "Failed" -> "✗ Failed"
                            else -> "In Progress"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = when (item.status) {
                            "Completed" -> Color.Green
                            "Failed" -> Color.Red
                            else -> Color(0xFFFFA500)  // Orange for In Progress
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                val subtitle = when (item.type) {
                    "Task" -> "General Task"
                    "Assignment" -> item.subject ?: "No Subject"
                    "Study Session" -> "${item.subject ?: "No Subject"} • ${item.duration ?: "No Duration"}"
                    "Reminder" -> "Reminder"
                    "Goal" -> item.subject ?: "No Subject"
                    else -> ""
                }
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
