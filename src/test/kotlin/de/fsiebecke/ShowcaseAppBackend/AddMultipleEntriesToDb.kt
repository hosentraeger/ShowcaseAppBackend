package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.plugins.*

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import java.time.LocalDateTime

class AddMultipleEntriesToDb {

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
    fun `create multiple device data entries successfully`() = runBlocking {
        val database = DatabaseFactory.getDatabase()

        // Anzahl der zu erstellenden Datensätze
        val numberOfEntries = 10

        transaction(database) {
            for (i in 1..numberOfEntries) {
                DataTable.insert {
                    it[deviceId] = "device$i" // Erstelle verschiedene deviceId
                    it[appVersion] = i // Version mit laufender Nummer
                    it[appId] = i // app_id basierend auf i
                    it[appLanguage] = "de"
                    it[appTheme] = AppThemeEnum.LIGHT // Beispiel Enum-Wert
                }
            }
        }

        // Überprüfe, ob die Datensätze erfolgreich erstellt wurden
        transaction(database) {
            val count = DataTable.selectAll().count().toInt()
            assertEquals(numberOfEntries, count) // Überprüfe, ob die erwartete Anzahl von Datensätzen existiert
        }
    }
}
