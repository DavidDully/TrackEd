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
fun ModuleDetailScreen(navController: NavController, moduleNumber: Int) {

    val module = ScienceModulesJson.navigation.firstOrNull { it.moduleNumber == moduleNumber }

    if (module == null) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Module Not Found") }) }
        ) { padding ->
            Box(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("Module data unavailable.")
            }
        }
        return
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(module.moduleTitle) }) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            module.topics.forEach { topic ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("topic/${topic.topicId}") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(topic.title, style = MaterialTheme.typography.titleMedium)
                        Text("${topic.subtopics.size} lessons", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
