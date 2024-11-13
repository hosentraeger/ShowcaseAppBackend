package de.fsiebecke.ShowcaseAppBackend.plugins

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class AppstartModel (
    val dataModelVersion: Int,
    val deviceId: String,
    @Contextual val lastLoginDateTime: LocalDateTime? = null,
    val mergeAssistantBlz: List<String?>? = null,
    @Contextual val mergeAssistantFromDateTime: LocalDateTime? = null,
    @Contextual val mergeAssistantToDateTime: LocalDateTime? = null,
    val minRecommendedVersion: List<String?>? = null,
    val minRequiredVersion: List<String?>? = null,
)
