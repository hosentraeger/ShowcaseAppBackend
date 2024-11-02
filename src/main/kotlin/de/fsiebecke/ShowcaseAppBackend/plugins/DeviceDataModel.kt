package de.fsiebecke.ShowcaseAppBackend.plugins

import kotlinx.serialization.Serializable

@Serializable
data class DeviceDataModel(
    val deviceId: String,
    val userName: String,
    val userDateTimeLastLogin: String,
    val userFcmToken: String,
    val appId: Int,
    // Füge hier weitere Felder hinzu, die du benötigst
)
