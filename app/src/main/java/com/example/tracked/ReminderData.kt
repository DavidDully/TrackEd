package com.example.tracked

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ReminderData(
    @SerialName("id")
    val id: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("reminder_text")
    val reminderText: String,

    @SerialName("reminder_date")
    val reminderDate: String? = null,

    @SerialName("completed")
    val completed: Boolean = false,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)
