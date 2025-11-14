package com.example.tracked

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyProgressData(
    @SerialName("id")
    val id: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("topic_id")
    val topicId: String,

    @SerialName("progress_percentage")
    val progressPercentage: Int = 0,

    @SerialName("last_studied")
    val lastStudied: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)
