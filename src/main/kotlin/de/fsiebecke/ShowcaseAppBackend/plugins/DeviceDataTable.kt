package de.fsiebecke.ShowcaseAppBackend.plugins

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DeviceDataTable : Table("device_data") {
    val deviceId = varchar("deviceId", 255).uniqueIndex()

    val systemSfStage = enumerationByName("system_sfStage", 50, SfStage::class)
    val systemMkaStage = enumerationByName("system_mkaStage", 50, MkaStage::class)
    val systemMkaLine = integer("system_mkaLine")

    val appVersion = varchar("app_version", 255)
    val appId = integer("app_id")
    val appLanguage = varchar("app_language", 10)
    val appTheme = enumerationByName("app_theme", 50, AppTheme::class)
    val appMaxSessionDuration = integer("app_maxSessionDuration")
    val appAutoUpdateAccounts = bool("app_autoUpdateAccounts")
    val appAccountSorting = integer("app_accountSorting")

    val userName = varchar("user_name", 255)
    val userFcmToken = varchar("user_fcmToken", 255)
    val userDateTimeInitialLogin = datetime("user_dateTimeInitialLogin")
    val userDateTimeLastLogin = datetime("user_dateTimeLastLogin")
    val userAgreeCollectStatistics = bool("user_agreeCollectStatistics")

    val subscriptionWero = bool("subscription_wero")
    val subscriptionKwitt = bool("subscription_kwitt")
    val subscriptionDiamond = bool("subscription_diamond")
    val subscriptionAloha = bool("subscription_aloha")
    val subscriptionBudgetBook = bool("subscription_budgetBook")

    val pushNotificationsAllowSystemNotifications = bool("pushNotifications_allowSystemNotifications")
    val pushNotificationsAllowIndividualOffers = bool("pushNotifications_allowIndividualOffers")
    val pushNotificationsAllowAccountAlarm = bool("pushNotifications_allowAccountAlarm")
    val pushNotificationsAllowNotificationBadge = bool("pushNotifications_allowNotificationBadge")
    val pushNotificationsLastPingNotificationReceived = datetime("pushNotifications_lastPingNotificationReceived")

    val metricsFullAppStarts = integer("metrics_fullAppStarts")
    val metricsFullAppStartsMs = integer("metrics_fullAppStartsMs")
    val metricsSubsequentAppStarts = integer("metrics_subsequentAppStarts")
    val metricsSubsequentAppStartsMs = integer("metrics_subsequentAppStartsMs")

    val deviceLastConnect = datetime("device_lastConnect")
    val deviceLastCommit = datetime("device_lastCommit")

    private fun InsertStatement<*>.fromDeviceData(deviceData: DeviceData) {
        this[deviceId] = deviceData.deviceMetaData!!.deviceId
        this[systemSfStage] = SfStage.BETA
        this[systemMkaStage] = MkaStage.ABN
        this[systemMkaLine] = 0
        this[appVersion] = deviceData.deviceReport?.appVersion ?: "DefaultAppVersion"
        this[appId] = (deviceData.deviceReport?.appVariant?:"0").toInt()
        this[appLanguage] = deviceData.featureSettings?.appLanguage?:"de"
        this[appTheme] = AppTheme.LIGHT
        this[appMaxSessionDuration] = deviceData.featureSettings?.maxSessionDuration?:5
        this[appAutoUpdateAccounts] = deviceData.featureSettings?.autoUpateBalance?:false
        this[appAccountSorting] = 0
        this[userName] = "fsiexp"
        this[userFcmToken] = deviceData.userData.push_id
        this[userDateTimeInitialLogin] = LocalDateTime.parse("1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userDateTimeLastLogin] = LocalDateTime.parse(deviceData.userData.last_login, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userAgreeCollectStatistics] = true
        this[subscriptionWero] = deviceData.deviceReport?.wero?:false
        this[subscriptionKwitt] = deviceData.deviceReport?.kwitt?:false
        this[subscriptionDiamond] = deviceData.deviceReport?.applePay?:false
        this[subscriptionAloha] = deviceData.deviceReport?.aloha?:false
        this[subscriptionBudgetBook] = deviceData.deviceReport?.budgetBook?:false
        this[pushNotificationsAllowSystemNotifications] = deviceData.deviceReport?.pushNotificationSettings?.system?:false
        this[pushNotificationsAllowIndividualOffers] = deviceData.deviceReport?.pushNotificationSettings?.individualOffers?:false
        this[pushNotificationsAllowAccountAlarm] = deviceData.deviceReport?.pushNotificationSettings?.accountAlarm?:false
        this[pushNotificationsAllowNotificationBadge] = deviceData.deviceReport?.pushNotificationSettings?.notificationBadge?:false
        this[pushNotificationsLastPingNotificationReceived] = LocalDateTime.parse(deviceData.deviceReport?.pushNotificationSettings?.lastPingNotificiationReceived?:"1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[metricsFullAppStarts] = deviceData.appMetrics?.fullAppStartsSinceLastCommit?:0
        this[metricsFullAppStartsMs] = deviceData.appMetrics?.fullAppStartsMsSinceLastCommit?:0
        this[metricsSubsequentAppStarts] = deviceData.appMetrics?.subsequentAppStartsSinceLastCommit?:0
        this[metricsSubsequentAppStartsMs] = deviceData.appMetrics?.subsequentAppStartsMsSinceLastCommit?:0
        this[deviceLastConnect] = LocalDateTime.parse(deviceData.deviceMetaData?.lastConnect?:"1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[deviceLastCommit] = LocalDateTime.parse(deviceData.deviceMetaData?.lastCommit?:"1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    private fun UpdateStatement.fromDeviceData(deviceData: DeviceData) {
        this[deviceId] = deviceData.deviceMetaData!!.deviceId
        this[systemSfStage] = SfStage.BETA
        this[systemMkaStage] = MkaStage.ABN
        this[systemMkaLine] = 0
        this[appVersion] = deviceData.deviceReport?.appVersion ?: "DefaultAppVersion"
        this[appId] = (deviceData.deviceReport?.appVariant?:"0").toInt()
        this[appLanguage] = deviceData.featureSettings?.appLanguage?:"de"
        this[appTheme] = AppTheme.LIGHT
        this[appMaxSessionDuration] = deviceData.featureSettings?.maxSessionDuration?:5
        this[appAutoUpdateAccounts] = deviceData.featureSettings?.autoUpateBalance?:false
        this[appAccountSorting] = 0
        this[userName] = "fsiexp"
        this[userFcmToken] = deviceData.userData.push_id
        this[userDateTimeInitialLogin] = LocalDateTime.parse("1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userDateTimeLastLogin] = LocalDateTime.parse(deviceData.userData.last_login, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userAgreeCollectStatistics] = true
        this[subscriptionWero] = deviceData.deviceReport?.wero?:false
        this[subscriptionKwitt] = deviceData.deviceReport?.kwitt?:false
        this[subscriptionDiamond] = deviceData.deviceReport?.applePay?:false
        this[subscriptionAloha] = deviceData.deviceReport?.aloha?:false
        this[subscriptionBudgetBook] = deviceData.deviceReport?.budgetBook?:false
        this[pushNotificationsAllowSystemNotifications] = deviceData.deviceReport?.pushNotificationSettings?.system?:false
        this[pushNotificationsAllowIndividualOffers] = deviceData.deviceReport?.pushNotificationSettings?.individualOffers?:false
        this[pushNotificationsAllowAccountAlarm] = deviceData.deviceReport?.pushNotificationSettings?.accountAlarm?:false
        this[pushNotificationsAllowNotificationBadge] = deviceData.deviceReport?.pushNotificationSettings?.notificationBadge?:false
        this[pushNotificationsLastPingNotificationReceived] = LocalDateTime.parse(deviceData.deviceReport?.pushNotificationSettings?.lastPingNotificiationReceived?:"1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[metricsFullAppStarts] = deviceData.appMetrics?.fullAppStartsSinceLastCommit?:0
        this[metricsFullAppStartsMs] = deviceData.appMetrics?.fullAppStartsMsSinceLastCommit?:0
        this[metricsSubsequentAppStarts] = deviceData.appMetrics?.subsequentAppStartsSinceLastCommit?:0
        this[metricsSubsequentAppStartsMs] = deviceData.appMetrics?.subsequentAppStartsMsSinceLastCommit?:0
        this[deviceLastConnect] = LocalDateTime.parse(deviceData.deviceMetaData?.lastConnect?:"1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[deviceLastCommit] = LocalDateTime.parse(deviceData.deviceMetaData?.lastCommit?:"1971-01-01T00:00:00.000000", DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    fun DeviceDataTable.updateOrInsert(deviceData: DeviceData): Int {
        val updatedRows = update({ deviceId eq deviceData.deviceMetaData!!.deviceId }) {
            it.fromDeviceData(deviceData) // für UpdateStatement
        }

        return if (updatedRows > 0) {
            updatedRows // Rückgabe der Anzahl der aktualisierten Zeilen
        } else {
            insert {
                it.fromDeviceData(deviceData) // für InsertStatement
            }
            1 // Rückgabe von 1, um anzuzeigen, dass ein Eintrag eingefügt wurde
        }
    }

    // Hilfsfunktion zum Abrufen aller Geräte
    fun selectAllDevices(): List<ResultRow> {
        return this.selectAll().toList() // Liste von ResultRow zurückgeben
    }
}
