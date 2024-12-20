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
            SchemaUtils.drop(DeviceTable) // Hier DeviceData verwenden
        }
        // Erstelle die Tabellen
        transaction(database) {
            SchemaUtils.create(DeviceTable) // Hier DeviceData verwenden
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
            val deviceDataModel = json.decodeFromString<DeviceDataModel>(jsonString)
            val appstartDataModel = json.decodeFromString<AppstartDataModel>(jsonString)
            val metricsDataModel = json.decodeFromString<MetricsDataModel>(jsonString)
            val featuresDataModel = json.decodeFromString<FeaturesDataModel>(jsonString)
            transaction(database) {
                DeviceTable.updateOrInsert(deviceDataModel, "0.0.0.42")
            }
            transaction(database) {
                AppstartTable.updateOrInsert(deviceDataModel.deviceId, appstartDataModel, "0.0.0.42")
            }
            transaction(database) {
                MetricsTable.updateOrInsert(deviceDataModel.deviceId, metricsDataModel, "0.0.0.42")
            }
            transaction(database) {
                FeaturesTable.updateOrInsert(deviceDataModel.deviceId, featuresDataModel, "0.0.0.42")
            }
        }
        // Überprüfe, ob die Datensätze erfolgreich erstellt wurden
        transaction(database) {
        }
    }
}
