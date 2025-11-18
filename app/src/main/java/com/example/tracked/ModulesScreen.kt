package com.example.tracked

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.tracked.ui.theme.PaleBlue
import com.example.tracked.ui.theme.AccentBlue
import com.example.tracked.ui.theme.SoftSurface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.DatePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.navigation.NavController
import android.content.Context
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModulesScreen(navController: NavController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Subjects") }) },
        containerColor = MaterialTheme.colorScheme.surface,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Top: subjects / modules area (about 2/3 of height)
            var modules by remember { mutableStateOf<List<ScienceModuleData>>(emptyList()) }
            var isLoadingModules by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                scope.launch {
                    try {
                        modules = SupabaseManager.getApi().getModules()
                        isLoadingModules = false
                    } catch (e: Exception) {
                        Log.e("Supabase", "Failed to load modules: ${e.message}")
                        scope.launch { snackbarHostState.showSnackbar("Failed to load modules") }
                        isLoadingModules = false
                    }
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isLoadingModules) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    modules.forEach { module ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navController.navigate("module/${module.id}") },
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Text(text = module.name, style = MaterialTheme.typography.titleLarge)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = module.description ?: "No description", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }


        }
    }
}
