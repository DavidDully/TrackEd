package com.example.tracked

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import android.util.Log
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopicScreen(navController: NavController, topicId: String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var topic by remember { mutableStateOf<TopicData?>(null) }
    var progress by remember { mutableStateOf<StudyProgressData?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                SupabaseManager.initialize(context)
                val topics = SupabaseManager.getApi().getTopics()
                topic = topics.firstOrNull { it.id == topicId }

                // Try to get existing progress for this topic
                val progressList = SupabaseManager.getApi().getStudyProgress()
                progress = progressList.firstOrNull { it.topicId == topicId }

                isLoading = false
            } catch (e: Exception) {
                Log.e("Supabase", "Failed to load topic: ${e.message}")
                errorMessage = "Failed to load topic"
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(topic?.name ?: "Topic") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            } else if (topic != null) {
                Text("Description: ${topic!!.description ?: "No description"}", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(16.dp))

                // Progress slider
                var currentProgress by remember { mutableStateOf(progress?.progressPercentage ?: 0) }

                Text("Progress: ${currentProgress}%", style = MaterialTheme.typography.titleMedium)
                Slider(
                    value = currentProgress.toFloat(),
                    onValueChange = { currentProgress = it.toInt() },
                    valueRange = 0f..100f,
                    steps = 100
                )

                Spacer(Modifier.height(16.dp))

                Button(onClick = {
                    scope.launch {
                        try {
                            val progressData = StudyProgressData(
                                id = progress?.id,
                                topicId = topicId,
                                progressPercentage = currentProgress
                            )
                            if (progress != null) {
                                SupabaseManager.getApi().updateStudyProgress(progress!!.id!!, progressData)
                            } else {
                                SupabaseManager.getApi().createStudyProgress(progressData)
                            }
                            // Refresh progress
                            val progressList = SupabaseManager.getApi().getStudyProgress()
                            progress = progressList.firstOrNull { it.topicId == topicId }
                        } catch (e: Exception) {
                            Log.e("Supabase", "Failed to save progress: ${e.message}")
                        }
                    }
                }) {
                    Text("Save Progress")
                }
            } else {
                Text("Topic not found", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

