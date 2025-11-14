package com.example.tracked

import androidx.compose.foundation.clickable
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
fun ModuleDetailScreen(navController: NavController, moduleId: String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var module by remember { mutableStateOf<ScienceModuleData?>(null) }
    var topics by remember { mutableStateOf<List<TopicData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                SupabaseManager.initialize(context)
                val modules = SupabaseManager.getApi().getModules()
                module = modules.firstOrNull { it.id == moduleId }
                if (module != null) {
                    topics = SupabaseManager.getApi().getTopics().filter { it.moduleId == moduleId }
                }
                isLoading = false
            } catch (e: Exception) {
                Log.e("Supabase", "Failed to load module details: ${e.message}")
                errorMessage = "Failed to load module details"
                isLoading = false
            }
        }
    }

    if (module == null && !isLoading) {
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
        topBar = { TopAppBar(title = { Text(module?.name ?: "Module") }) }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
            } else {
                topics.forEach { topic ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("topic/${topic.id}") },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(topic.name, style = MaterialTheme.typography.titleMedium)
                            Text(topic.description ?: "No description", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}
