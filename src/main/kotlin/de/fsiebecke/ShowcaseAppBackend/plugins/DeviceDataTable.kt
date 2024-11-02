package de.fsiebecke.ShowcaseAppBackend.plugins

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.selectAll

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

    // Für usage.features könntest du eine separate Tabelle erstellen und einen Link hierher setzen.
    // val usageFeatures = reference("usage_features", UsageFeaturesTable) // Beispiel


    // Hilfsfunktion zum Abrufen aller Geräte
    fun selectAllDevices(): List<ResultRow> {
        return this.selectAll().toList() // Liste von ResultRow zurückgeben
    }

    // Hilfsfunktion zum Konvertieren einer ResultRow in DeviceDataModel
    fun toDeviceData(row: ResultRow): DeviceDataModel {
        return DeviceDataModel(
            deviceId = row[deviceId],
            userName = row[userName],
            userDateTimeLastLogin = row[userDateTimeLastLogin].toString(), // formatieren, wie du möchtest
            userFcmToken = row[userFcmToken],
            appId = row[appId]
            // Füge hier die anderen Felder hinzu, die du brauchst
        )
    }
}
