package de.fsiebecke.ShowcaseAppBackend.plugins

import de.fsiebecke.ShowcaseAppBackend.CURRENT_DATA_MODEL_VERSION
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        post("/appstart") {
            val result = 0

            // Antwort auf den HTTP-Request nach der Transaktion zurückgeben
            if (result > 0) {
                call.respond(HttpStatusCode.OK, "Daten erfolgreich erhalten und verarbeitet")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Daten konnten nicht verarbeitet werden")
            }
        }

        put("/deviceData/v2") {
            val clientIp = call.request.origin.remoteHost
            val dataModel = call.receive<DeviceDataModel>() // Empfangen der Device-Daten als Model
            if ( dataModel.dataModelVersion != CURRENT_DATA_MODEL_VERSION) {
                call.respond(HttpStatusCode.BadRequest, "Daten konnten nicht verarbeitet werden")
            }
            val database = DatabaseFactory.getDatabase() // Holen der Datenbankverbindung

            // Asynchrone Datenbankoperation
            val result = withContext(Dispatchers.IO) {
                transaction(database) {
                    // Update oder Insert durchführen und die Anzahl der betroffenen Zeilen zurückgeben
                    DeviceTable.updateOrInsert(dataModel, clientIp)
                }
            }

            // Antwort auf den HTTP-Request nach der Transaktion zurückgeben
            if (result > 0) {
                call.respond(HttpStatusCode.OK, "Daten erfolgreich erhalten und verarbeitet")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Daten konnten nicht verarbeitet werden")
            }
        }

        get("/deviceData") {
            // Verwende eine Coroutine, um die Transaction zu handhaben
            val devices = withContext(Dispatchers.IO) {
                transaction {
                    DeviceTable.selectAllRecords().map { DeviceTable.toModel(it) }
                }
            }
            call.respond(devices) // Hier kann call.respond aufgerufen werden
        }

// Endpunkt zum Abrufen von Gerätedetails
        get("/devices/deviceDetails/{deviceId}") {
            val deviceId =
                call.parameters["deviceId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing deviceId")

            // Verwende eine Coroutine, um die Transaction zu handhaben
            val device = withContext(Dispatchers.IO) {
                transaction {
                    DeviceTable.selectAll().where { DeviceTable.deviceId eq deviceId }.singleOrNull()
                }
            }

            if (device != null) {
                call.respond(DeviceTable.toModel(device))
            } else {
                call.respond(HttpStatusCode.NotFound, "Device not found")
            }
            call.respond(HttpStatusCode.BadRequest, "Daten konnten nicht verarbeitet werden")
        }
// Endpunkt zum Abrufen von Metrics
        get("/devices/metrics/{deviceId}") {
            val deviceId =
                call.parameters["deviceId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing deviceId")

            // Verwende eine Coroutine, um die Transaction zu handhaben
            val data = withContext(Dispatchers.IO) {
                transaction {
                    MetricsTable.selectAll().where { MetricsTable.deviceId eq deviceId }.singleOrNull()
                }
            }

            if (data != null) {
                call.respond(MetricsTable.toModel(data))
            } else {
                call.respond(HttpStatusCode.NotFound, "Device not found")
            }
            call.respond(HttpStatusCode.BadRequest, "Daten konnten nicht verarbeitet werden")
        }

// Endpunkt zum Abrufen von Appstart-Daten
        get("/devices/appstart/{deviceId}") {
            val deviceId =
                call.parameters["deviceId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing deviceId")

            // Verwende eine Coroutine, um die Transaction zu handhaben
            val data = withContext(Dispatchers.IO) {
                transaction {
                    AppstartTable.selectAll().where { AppstartTable.deviceId eq deviceId }.singleOrNull()
                }
            }

            if (data != null) {
                call.respond(AppstartTable.toModel(data))
            } else {
                call.respond(HttpStatusCode.NotFound, "Device not found")
            }
            call.respond(HttpStatusCode.BadRequest, "Daten konnten nicht verarbeitet werden")
        }

// Endpunkt zum Abrufen von Features
        get("/devices/features/{deviceId}") {
            val deviceId =
                call.parameters["deviceId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing deviceId")

            // Verwende eine Coroutine, um die Transaction zu handhaben
            val data = withContext(Dispatchers.IO) {
                transaction {
                    FeaturesTable.selectAll().where { FeaturesTable.deviceId eq deviceId }.singleOrNull()
                }
            }

            if (data != null) {
                call.respond(FeaturesTable.toModel(data))
            } else {
                call.respond(HttpStatusCode.NotFound, "Device not found")
            }
            call.respond(HttpStatusCode.BadRequest, "Daten konnten nicht verarbeitet werden")
        }

// Endpunkt zum Speichern von Features
        post("/devices/updateFeatures") {
            try {
                // JSON aus der Anfrage empfangen
                val featureUpdateRequest = call.receive<FeaturesDataModel>()
                val clientIp = call.request.origin.remoteHost

                // Daten in die Datenbank speichern (Update oder Insert)
                val result = withContext(Dispatchers.IO) {
                    transaction {
                        FeaturesTable.updateOrInsert(featureUpdateRequest.deviceId,featureUpdateRequest, clientIp)
                    }
                }

                if (result > 0) {
                    call.respond(HttpStatusCode.OK, "Device features successfully updated or inserted")
                } else {
                    call.respond(HttpStatusCode.InternalServerError, "Failed to update or insert device features")
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid data format or error: ${e.message}")
            }
        }

        // Endpunkt zum Löschen eines Geräts
        delete("/devices/{deviceId}") {
            val deviceId =
                call.parameters["deviceId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing deviceId")

            // Verwende eine Coroutine, um die Transaction zu handhaben
            val deletedCount = withContext(Dispatchers.IO) {
                transaction {
                    DeviceTable.deleteWhere { DeviceTable.deviceId eq deviceId }
                }
            }

            if (deletedCount > 0) {
                call.respond(HttpStatusCode.OK, "Device deleted successfully.")
            } else {
                call.respond(HttpStatusCode.NotFound, "Device not found.")
            }
        }

        // Empfangen des Formulars und Senden der Push-Benachrichtigung
        post("/sendPushNotification") {
            val pushNotificationBuilder = PushNotificationBuilder()

            val pushNotificationData = call.receive<PushNotificationData>()

            // Überprüfe, ob pushId null ist
            if (pushNotificationData.deviceId.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "Device ID cannot be null")
                return@post // Beende die Ausführung, falls pushId null ist
            }
            if (pushNotificationData.fcmToken.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "FCM Token cannot be null")
                return@post // Beende die Ausführung, falls pushId null ist
            }
            // Push Notification Logik
            val success = pushNotificationBuilder.sendPushNotification(pushNotificationData)

            if (success) {
                call.respond(HttpStatusCode.OK, mapOf("status" to "success", "message" to "Push notification sent successfully!"))
            } else {
                call.respond(HttpStatusCode.InternalServerError, mapOf("status" to "error", "message" to "Failed to send push notification."))
            }
        }
    }
}
