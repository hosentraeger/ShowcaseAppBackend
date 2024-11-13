package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.plugins.*

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach

class AddSpecialRecordToDb {
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
            }
        }

        @AfterEach
        fun tearDown() {
            // Lösche die Tabellen nach jedem Test
            /*
            val database = DatabaseFactory.getDatabase()
            transaction(database) {
                SchemaUtils.drop(DeviceData) // Hier DeviceData verwenden
            }
            */
        }

        @Test
        fun `create special device data entry successfully`() = runBlocking {
            val database = DatabaseFactory.getDatabase()

            transaction(database) {
                DeviceDataTable.insert {
                    it[deviceId] = "3ec55ba9-cabb-4358-91c9-5c29a2f58a86"
                    it[appVersion] = 1
                    it[appId] = 7
                    it[appLanguage] = "de"
                }
            transaction(database) {
            }
        }
    }
}