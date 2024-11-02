package de.fsiebecke.ShowcaseAppBackend.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/deviceData") {
            // Verwende eine Coroutine, um die Transaction zu handhaben
            val devices = withContext(Dispatchers.IO) {
                transaction {
                    DeviceDataTable.selectAllDevices().map { DeviceDataTable.toDeviceData(it) }
                }
            }
            call.respond(devices) // Hier kann call.respond aufgerufen werden
        }

// Endpunkt zum Abrufen von Gerätedetails
        get("/devices/{deviceId}") {
            val deviceId =
                call.parameters["deviceId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "Missing deviceId")

            // Verwende eine Coroutine, um die Transaction zu handhaben
            val device = withContext(Dispatchers.IO) {
                transaction {
                    DeviceDataTable.selectAll().where { DeviceDataTable.deviceId eq deviceId }.singleOrNull()
                }
            }

            if (device != null) {
                call.respond(DeviceDataTable.toDeviceData(device))
            } else {
                call.respond(HttpStatusCode.NotFound, "Device not found")
            }
        }

// Endpunkt zum Löschen eines Geräts
        delete("/devices/{deviceId}") {
            val deviceId =
                call.parameters["deviceId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Missing deviceId")

            // Verwende eine Coroutine, um die Transaction zu handhaben
            val deletedCount = withContext(Dispatchers.IO) {
                transaction {
                    DeviceDataTable.deleteWhere { DeviceDataTable.deviceId eq deviceId }
                }
            }

            if (deletedCount > 0) {
                call.respond(HttpStatusCode.OK, "Device deleted successfully.")
            } else {
                call.respond(HttpStatusCode.NotFound, "Device not found.")
            }
        }
    }
}
