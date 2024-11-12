package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.plugins.*

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
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
            SchemaUtils.drop(DataTable) // Hier DeviceData verwenden
        }
        // Erstelle die Tabellen
        transaction(database) {
            SchemaUtils.create(DataTable) // Hier DeviceData verwenden
        }
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `add record to new db entries successfully`() = runBlocking {
        val database = DatabaseFactory.getDatabase()
        val jsonString = """
            {
                "deviceId": "44badab1-901e-489f-bb98-e93aedd26827",
                "primaryUserName": "fsiexp"
            }
        """.trimIndent()
        val dataModel = Json.decodeFromString<DataModel>(jsonString)
        transaction(database) {
            DataTable.updateOrInsert(dataModel, "0.0.0.42")
        }

        // Überprüfe, ob die Datensätze erfolgreich erstellt wurden
        transaction(database) {
        }
    }
}
