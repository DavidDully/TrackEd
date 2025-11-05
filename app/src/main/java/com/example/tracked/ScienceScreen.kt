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
fun ScienceScreen(navController: NavController) {
    val modules = ScienceModulesJson.navigation

    Scaffold(
        topBar = { TopAppBar(title = { Text("Science Modules") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            modules.forEach { module ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("module/${module.moduleNumber}") }

                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = module.moduleTitle, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Module Number: ${module.moduleNumber}")
                    }
                }
            }
        }
    }
}
