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

class DeviceDataModel {

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
    }

    @Test
    fun `create multiple device data entries successfully`() = runBlocking {
        val database = DatabaseFactory.getDatabase()

        // Anzahl der zu erstellenden Datensätze
        val numberOfEntries = 10

        transaction(database) {
            for (i in 1..numberOfEntries) {
                DeviceDataTable.insert {
                    it[deviceId] = "device$i" // Erstelle verschiedene deviceId
                    it[systemSfStage] = SfStage.TEST // Beispiel Enum-Wert
                    it[systemMkaStage] = MkaStage.ENT // Beispiel Enum-Wert
                    it[systemMkaLine] = i // Setze system_mkaLine basierend auf i
                    it[appVersion] = "1.0.$i" // Version mit laufender Nummer
                    it[appId] = i // app_id basierend auf i
                    it[appLanguage] = "de"
                    it[appTheme] = AppTheme.LIGHT // Beispiel Enum-Wert
                    it[appMaxSessionDuration] = 3600
                    it[appAutoUpdateAccounts] = i % 2 == 0 // Abwechselnd true/false
                    it[appAccountSorting] = i
                    it[userName] = "Test User $i" // Unterschiedlicher Benutzername
                    it[userFcmToken] = "fcm_token_$i" // Unterschiedlicher Token
                    it[userDateTimeInitialLogin] = LocalDateTime.now()
                    it[userDateTimeLastLogin] = LocalDateTime.now()
                    it[userAgreeCollectStatistics] = true
                    it[subscriptionWero] = i % 2 == 0 // Abwechselnd true/false
                    it[subscriptionKwitt] = true
                    it[subscriptionDiamond] = i % 3 == 0 // Nur jede 3. Subscription
                    it[subscriptionAloha] = false
                    it[subscriptionBudgetBook] = true
                    it[pushNotificationsAllowSystemNotifications] = true
                    it[pushNotificationsAllowIndividualOffers] = true
                    it[pushNotificationsAllowAccountAlarm] = true
                    it[pushNotificationsAllowNotificationBadge] = false
                    it[pushNotificationsLastPingNotificationReceived] = LocalDateTime.now()
                    it[metricsFullAppStarts] = i * 5 // Beispielwert
                    it[metricsFullAppStartsMs] = i * 1000 // Beispielwert
                    it[metricsSubsequentAppStarts] = i // Beispielwert
                    it[metricsSubsequentAppStartsMs] = i * 500 // Beispielwert
                    it[deviceLastConnect] = LocalDateTime.now()
                    it[deviceLastCommit] = LocalDateTime.now()
                }
            }
        }

        // Überprüfe, ob die Datensätze erfolgreich erstellt wurden
        transaction(database) {
            val count = DeviceDataTable.selectAll().count().toInt()
            assertEquals(numberOfEntries, count) // Überprüfe, ob die erwartete Anzahl von Datensätzen existiert
        }
    }
}
