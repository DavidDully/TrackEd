package com.example.tracked

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModulesScreen(navController: NavController) {

    Scaffold(
        topBar = { TopAppBar(title = { Text("Subjects") }) },
        containerColor = Color(0xFFF5F5F5)  // Light gray background
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // SCIENCE SUBJECT CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("science") },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Science", style = MaterialTheme.typography.titleMedium)
                    Text(text = "View Modules", style = MaterialTheme.typography.bodyMedium)
                }
            }

            // (Optional) You can add more subjects here later:
            /*
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("math") },
            ) { Text("Math") }
            */
        }
    }
}
