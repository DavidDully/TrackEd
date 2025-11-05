package com.example.tracked

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicScreen(navController: NavController, topicId: String) {
    val module = ScienceModulesJson.navigation.firstOrNull { m ->
        m.topics.any { it.topicId == topicId }
    }
    val topic = module?.topics?.first { it.topicId == topicId }

    Scaffold(
        topBar = { TopAppBar(title = { Text(topic?.title ?: "Topic") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            topic?.subtopics?.forEach { lesson ->
                Text("• $lesson", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

