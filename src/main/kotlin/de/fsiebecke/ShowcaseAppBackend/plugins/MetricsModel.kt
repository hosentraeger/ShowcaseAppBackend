package de.fsiebecke.ShowcaseAppBackend.plugins

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class MetricsModel(
    val dataModelVersion: Int,
    val deviceId: String,
    @Contextual val lastLoginDateTime: LocalDateTime? = null,
    val fullAppStartsMsSinceLastCommit: Int? = null,
    val fullAppStartsSinceLastCommit: Int? = null,
    val subsequentAppStartsMsSinceLastCommit: Int? = null,
    val subsequentAppStartsSinceLastCommit: Int? = null,
)
