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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModulesScreen(navController: NavController) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Subjects") }) },
        containerColor = PaleBlue,
        snackbarHost = { hostState ->
            SnackbarHost(hostState = hostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = AccentBlue.copy(alpha = 0.95f),
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Top: subjects / modules area (about 2/3 of height)
            Column(modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // SCIENCE SUBJECT CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("science") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(text = "Science", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "View Modules", style = MaterialTheme.typography.bodyMedium)
                    }
                }

                // (Optional) You can add more subjects here later
            }

            // Bottom: reminder and calendar (about 1/3 of height)
            val context = LocalContext.current
            var reminderText by remember { mutableStateOf("") }
            var selectedDate by remember { mutableStateOf("") }

            // load saved values
            LaunchedEffect(Unit) {
                val prefs = context.getSharedPreferences("track_prefs", Context.MODE_PRIVATE)
                reminderText = prefs.getString("reminder_text", "") ?: ""
                selectedDate = prefs.getString("reminder_date", "") ?: ""
            }

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            var showDatePicker by rememberSaveable { mutableStateOf(false) }
            val datePickerState = rememberDatePickerState()

            Row(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Reminder input (fillable) with Save/Clear
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                    Text(text = "Reminder", style = MaterialTheme.typography.titleSmall)
                    OutlinedTextField(
                        value = reminderText,
                        onValueChange = { reminderText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        placeholder = { Text("Add a reminder...") },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = AccentBlue,
                            unfocusedBorderColor = AccentBlue.copy(alpha = 0.35f),
                            containerColor = SoftSurface,
                        ),
                        singleLine = false,
                        maxLines = 3
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                        Button(onClick = {
                            val prefs = context.getSharedPreferences("track_prefs", Context.MODE_PRIVATE)
                            prefs.edit().putString("reminder_text", reminderText).apply()
                            scope.launch { snackbarHostState.showSnackbar("Reminder saved") }
                        }) {
                            Text("Save")
                        }

                        OutlinedButton(onClick = {
                            reminderText = ""
                            val prefs = context.getSharedPreferences("track_prefs", Context.MODE_PRIVATE)
                            prefs.edit().remove("reminder_text").apply()
                            scope.launch { snackbarHostState.showSnackbar("Reminder cleared") }
                        }) {
                            Text("Clear")
                        }
                    }
                }

                // Calendar input (select date)
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()) {
                    Text(text = "Calendar", style = MaterialTheme.typography.titleSmall)
                    OutlinedTextField(
                        value = if (selectedDate.isEmpty()) "Select a date" else selectedDate,
                        onValueChange = { /* readOnly */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) { Icon(Icons.Default.DateRange, contentDescription = "Pick date") }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = AccentBlue,
                            unfocusedBorderColor = AccentBlue.copy(alpha = 0.35f),
                            containerColor = SoftSurface,
                        )
                    )
                }
            }

            // Compose Material DatePicker dialog
            if (showDatePicker) {
                AlertDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val millis = datePickerState.selectedDateMillis
                            if (millis != null) {
                                selectedDate = sdf.format(Date(millis))
                                val prefs = context.getSharedPreferences("track_prefs", Context.MODE_PRIVATE)
                                prefs.edit().putString("reminder_date", selectedDate).apply()
                                scope.launch { snackbarHostState.showSnackbar("Date saved") }
                            }
                            showDatePicker = false
                        }) { Text("OK") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                    },
                    text = {
                        DatePicker(state = datePickerState)
                    }
                )
            }
        }
    }
}
