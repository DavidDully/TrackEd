package com.example.tracked

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScienceModuleData(
    @SerialName("id")
    val id: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("description")
    val description: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("updated_at")
    val updatedAt: String? = null
)
