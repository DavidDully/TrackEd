package com.example.tracked

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage

@Composable
fun ProfileScreen() {
    // Basic states
    var userName by remember { mutableStateOf(TextFieldValue("John Doe")) }
    var isEditingName by remember { mutableStateOf(false) }

    var isDarkMode by remember { mutableStateOf(false) }
    var isNotificationsEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English") }

    var profileImageUri by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Profile Picture Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUri == null) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            }
        }

        Text(
            text = "Tap to change photo",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Your Name",
                    style = MaterialTheme.typography.titleMedium
                )

                if (isEditingName) {
                    OutlinedTextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("Enter your name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { isEditingName = false }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { isEditingName = false }) {
                            Text("Save")
                        }
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (userName.text.isEmpty()) "No name set" else userName.text,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(onClick = { isEditingName = true }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Name"
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Settings Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Settings", style = MaterialTheme.typography.titleMedium)

                // Dark Mode Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Dark Mode", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { isDarkMode = it }
                    )
                }

                // Notifications Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Notifications", style = MaterialTheme.typography.bodyLarge)
                    Switch(
                        checked = isNotificationsEnabled,
                        onCheckedChange = { isNotificationsEnabled = it }
                    )
                }

                // Language Preference
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Language", style = MaterialTheme.typography.bodyLarge)
                    TextButton(onClick = { /* TODO: show language picker */ }) {
                        Text(selectedLanguage)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Account Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Account", style = MaterialTheme.typography.titleMedium)

                TextButton(onClick = { /* TODO: open privacy policy */ }) {
                    Text("Privacy Policy")
                }

                TextButton(onClick = { /* TODO: open about app */ }) {
                    Text("About App")
                }

                TextButton(onClick = { /* TODO: handle logout */ }) {
                    Text("Log Out", color = MaterialTheme.colorScheme.error)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer - App Version
        Text(
            text = "App Version 1.0.0",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
