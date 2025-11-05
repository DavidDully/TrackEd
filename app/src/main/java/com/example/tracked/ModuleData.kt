package com.example.tracked

import kotlinx.serialization.Serializable

@Serializable
data class NavigationData(
    val navigation: List<ModuleData>
)

@Serializable
data class ModuleData(
    val module_number: Int,
    val module_title: String,
    val topics: List<TopicData>
)

@Serializable
data class TopicData(
    val topic_id: String,
    val title: String,
    val subtopics: List<String>
)
