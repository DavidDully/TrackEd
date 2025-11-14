package com.example.tracked

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

object DatabaseSeeder {
    private val json = Json { ignoreUnknownKeys = true }

    fun seedDatabaseIfEmpty(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val api = SupabaseManager.getApi()

                // Check if modules already exist
                val existingModules = api.getModules()
                if (existingModules.isNotEmpty()) {
                    Log.d("Seeder", "Database already seeded")
                    return@launch
                }

                // Load JSON from assets
                val jsonString = context.assets.open("science_modules.json").bufferedReader().use { it.readText() }
                val navigationData = json.decodeFromString<NavigationData>(jsonString)

                // Seed modules and topics
                for (module in navigationData.navigation) {
                    val moduleData = ScienceModuleData(
                        name = module.module_title,
                        description = "Module ${module.module_number}"
                    )
                    val createdModule = api.createModule(moduleData)

                    for (topic in module.topics) {
                        val topicData = TopicData(
                            moduleId = createdModule.id!!,
                            name = topic.title,
                            description = topic.subtopics.joinToString(", ")
                        )
                        api.createTopic(topicData)
                    }
                }

                Log.d("Seeder", "Database seeded successfully")
            } catch (e: Exception) {
                Log.e("Seeder", "Failed to seed database: ${e.message}")
            }
        }
    }
}
