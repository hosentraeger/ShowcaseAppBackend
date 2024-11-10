package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.plugins.*

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import java.time.LocalDateTime

class AddSpecialRecordToDb {
        @BeforeEach
        fun setUp() {
            // Holen der Datenbankinstanz
            val database = DatabaseFactory.getDatabase()

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
                    it[systemSfStage] = SfStage.BETA
                    it[systemMkaStage] = MkaStage.ABN
                    it[systemMkaLine] = 1
                    it[appVersion] = "1.0.0"
                    it[appId] = 7
                    it[appLanguage] = "de"
                    it[appTheme] = AppTheme.LIGHT
                    it[appMaxSessionDuration] = 5*60
                    it[appAutoUpdateAccounts] = true
                    it[appAccountSorting] = 0
                    it[userName] = "fsiexp"
                    it[userFcmToken] = "ejLTpa0hRzeclXoGrseKEi:APA91bHd0cp_jdt_cqPx2mVb9fwXG0NTE2UKzODMyUrhy4PhfEff18EXDpm9Vdqibrf31ICNPsEdUjFRn_H4zyQajHYJ9T8K1wfD6rxjSA2WwGWkcbhbZoc" // Unterschiedlicher Token
                    it[userDateTimeInitialLogin] = LocalDateTime.now()
                    it[userDateTimeLastLogin] = LocalDateTime.now()
                    it[userAgreeCollectStatistics] = true
                    it[subscriptionWero] = true
                    it[subscriptionKwitt] = true
                    it[subscriptionDiamond] = false
                    it[subscriptionAloha] = false
                    it[subscriptionBudgetBook] = true
                    it[pushNotificationsAllowSystemNotifications] = true
                    it[pushNotificationsAllowIndividualOffers] = true
                    it[pushNotificationsAllowAccountAlarm] = true
                    it[pushNotificationsAllowNotificationBadge] = false
                    it[pushNotificationsLastPingNotificationReceived] = LocalDateTime.now()
                    it[metricsFullAppStarts] = 1
                    it[metricsFullAppStartsMs] = 3000
                    it[metricsSubsequentAppStarts] = 4
                    it[metricsSubsequentAppStartsMs] = 10000
                    it[deviceLastConnect] = LocalDateTime.now()
                    it[deviceLastCommit] = LocalDateTime.now()
                }
            transaction(database) {
            }
        }
    }
}