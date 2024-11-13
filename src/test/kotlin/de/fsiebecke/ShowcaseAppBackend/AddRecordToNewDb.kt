package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.plugins.*

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach

class AddRecordToNewDb {

@BeforeEach
    fun setUp() {
        // Holen der Datenbankinstanz
        val database = DatabaseFactory.getDatabase()

        // Lösche die Tabellen vor jedem Test
        transaction(database) {
            SchemaUtils.drop(DeviceDataTable) // Hier DeviceData verwenden
        }
        // Erstelle die Tabellen
        transaction(database) {
            SchemaUtils.create(DeviceDataTable) // Hier DeviceData verwenden
            SchemaUtils.create(FeaturesTable)
            SchemaUtils.create(MetricsTable)
            SchemaUtils.create(AppstartTable)
        }
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `add record to new db entries successfully`() = runBlocking {
        val database = DatabaseFactory.getDatabase()
        val json = createJson() // Verwende das erstellte Json mit den richtigen Serialisierern
        testData.forEach { jsonString ->
            val dataModel = json.decodeFromString<DeviceDataModel>(jsonString)
            val appstartModel = json.decodeFromString<AppstartModel>(jsonString)
            val metricsModel = json.decodeFromString<MetricsModel>(jsonString)
            val featuresModel = json.decodeFromString<FeaturesModel>(jsonString)
            transaction(database) {
                DeviceDataTable.updateOrInsert(dataModel, "0.0.0.42")
            }
            transaction(database) {
                AppstartTable.updateOrInsert(dataModel.deviceId, appstartModel, "0.0.0.42")
            }
            transaction(database) {
                MetricsTable.updateOrInsert(dataModel.deviceId, metricsModel, "0.0.0.42")
            }
            transaction(database) {
                FeaturesTable.updateOrInsert(dataModel.deviceId, featuresModel, "0.0.0.42")
            }
        }
        // Überprüfe, ob die Datensätze erfolgreich erstellt wurden
        transaction(database) {
        }
    }
}
