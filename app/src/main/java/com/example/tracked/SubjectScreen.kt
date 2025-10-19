package com.example.tracked

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource // If using a drawable icon; otherwise, use Text for emoji
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SubjectScreen(navController: NavController) {
    // Main column layout for the screen, centered and padded
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title text for the screen
        Text(
            text = "Subjects",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Card for the Science subject (only one for now, as per MVP)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("science_study") }, // Navigate to placeholder screen on tap
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon for Science (using emoji for simplicity; replace with painterResource if using drawable)
                Text(
                    text = "🧪", // Science icon (test tube emoji)
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(end = 16.dp)
                )

                // Column for subject name and description
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Science",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Explore and understand the world around you.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}