package de.fsiebecke.ShowcaseAppBackend.plugins

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.Statement
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

    // Für usage.features könntest du eine separate Tabelle erstellen und einen Link hierher setzen.
    // val usageFeatures = reference("usage_features", UsageFeaturesTable) // Beispiel

    fun toDeviceData(row: ResultRow): DeviceDataModel {
        return DeviceDataModel(
            deviceId = row[deviceId],
            systemSfStage = row[systemSfStage],
            systemMkaStage = row[systemMkaStage],
            systemMkaLine = row[systemMkaLine],
            appVersion = row[appVersion],
            appId = row[appId],
            appLanguage = row[appLanguage],
            appTheme = row[appTheme],
            appMaxSessionDuration = row[appMaxSessionDuration],
            appAutoUpdateAccounts = row[appAutoUpdateAccounts],
            appAccountSorting = row[appAccountSorting],
            userName = row[userName],
            userFcmToken = row[userFcmToken],
            userDateTimeInitialLogin = row[userDateTimeInitialLogin].toString(),
            userDateTimeLastLogin = row[userDateTimeLastLogin].toString(),
            userAgreeCollectStatistics = row[userAgreeCollectStatistics],
            subscriptionWero = row[subscriptionWero],
            subscriptionKwitt = row[subscriptionKwitt],
            subscriptionDiamond = row[subscriptionDiamond],
            subscriptionAloha = row[subscriptionAloha],
            subscriptionBudgetBook = row[subscriptionBudgetBook],
            pushNotificationsAllowSystemNotifications = row[pushNotificationsAllowSystemNotifications],
            pushNotificationsAllowIndividualOffers = row[pushNotificationsAllowIndividualOffers],
            pushNotificationsAllowAccountAlarm = row[pushNotificationsAllowAccountAlarm],
            pushNotificationsAllowNotificationBadge = row[pushNotificationsAllowNotificationBadge],
            pushNotificationsLastPingNotificationReceived = row[pushNotificationsLastPingNotificationReceived].toString(),
            metricsFullAppStarts = row[metricsFullAppStarts],
            metricsFullAppStartsMs = row[metricsFullAppStartsMs],
            metricsSubsequentAppStarts = row[metricsSubsequentAppStarts],
            metricsSubsequentAppStartsMs = row[metricsSubsequentAppStartsMs],
            deviceLastConnect = row[deviceLastConnect].toString(),
            deviceLastCommit = row[deviceLastCommit].toString()
        )
    }

    // Erweiterungsfunktion für InsertStatement
    private fun InsertStatement<*>.fromDeviceData(deviceData: DeviceDataModel) {
        this[deviceId] = deviceData.deviceId
        this[systemSfStage] = deviceData.systemSfStage
        this[systemMkaStage] = deviceData.systemMkaStage
        this[systemMkaLine] = deviceData.systemMkaLine
        this[appVersion] = deviceData.appVersion
        this[appId] = deviceData.appId
        this[appLanguage] = deviceData.appLanguage
        this[appTheme] = deviceData.appTheme
        this[appMaxSessionDuration] = deviceData.appMaxSessionDuration
        this[appAutoUpdateAccounts] = deviceData.appAutoUpdateAccounts
        this[appAccountSorting] = deviceData.appAccountSorting
        this[userName] = deviceData.userName
        this[userFcmToken] = deviceData.userFcmToken
        this[userDateTimeInitialLogin] = LocalDateTime.parse(deviceData.userDateTimeInitialLogin, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userDateTimeLastLogin] = LocalDateTime.parse(deviceData.userDateTimeLastLogin, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userAgreeCollectStatistics] = deviceData.userAgreeCollectStatistics
        this[subscriptionWero] = deviceData.subscriptionWero
        this[subscriptionKwitt] = deviceData.subscriptionKwitt
        this[subscriptionDiamond] = deviceData.subscriptionDiamond
        this[subscriptionAloha] = deviceData.subscriptionAloha
        this[subscriptionBudgetBook] = deviceData.subscriptionBudgetBook
        this[pushNotificationsAllowSystemNotifications] = deviceData.pushNotificationsAllowSystemNotifications
        this[pushNotificationsAllowIndividualOffers] = deviceData.pushNotificationsAllowIndividualOffers
        this[pushNotificationsAllowAccountAlarm] = deviceData.pushNotificationsAllowAccountAlarm
        this[pushNotificationsAllowNotificationBadge] = deviceData.pushNotificationsAllowNotificationBadge
        this[pushNotificationsLastPingNotificationReceived] = LocalDateTime.parse(deviceData.pushNotificationsLastPingNotificationReceived, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[metricsFullAppStarts] = deviceData.metricsFullAppStarts
        this[metricsFullAppStartsMs] = deviceData.metricsFullAppStartsMs
        this[metricsSubsequentAppStarts] = deviceData.metricsSubsequentAppStarts
        this[metricsSubsequentAppStartsMs] = deviceData.metricsSubsequentAppStartsMs
        this[deviceLastConnect] = LocalDateTime.parse(deviceData.deviceLastConnect, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[deviceLastCommit] = LocalDateTime.parse(deviceData.deviceLastCommit, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    // Erweiterungsfunktion für UpdateStatement
    private fun UpdateStatement.fromDeviceData(deviceData: DeviceDataModel) {
        this[deviceId] = deviceData.deviceId
        this[systemSfStage] = deviceData.systemSfStage
        this[systemMkaStage] = deviceData.systemMkaStage
        this[systemMkaLine] = deviceData.systemMkaLine
        this[appVersion] = deviceData.appVersion
        this[appId] = deviceData.appId
        this[appLanguage] = deviceData.appLanguage
        this[appTheme] = deviceData.appTheme
        this[appMaxSessionDuration] = deviceData.appMaxSessionDuration
        this[appAutoUpdateAccounts] = deviceData.appAutoUpdateAccounts
        this[appAccountSorting] = deviceData.appAccountSorting
        this[userName] = deviceData.userName
        this[userFcmToken] = deviceData.userFcmToken
        this[userDateTimeInitialLogin] = LocalDateTime.parse(deviceData.userDateTimeInitialLogin, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userDateTimeLastLogin] = LocalDateTime.parse(deviceData.userDateTimeLastLogin, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[userAgreeCollectStatistics] = deviceData.userAgreeCollectStatistics
        this[subscriptionWero] = deviceData.subscriptionWero
        this[subscriptionKwitt] = deviceData.subscriptionKwitt
        this[subscriptionDiamond] = deviceData.subscriptionDiamond
        this[subscriptionAloha] = deviceData.subscriptionAloha
        this[subscriptionBudgetBook] = deviceData.subscriptionBudgetBook
        this[pushNotificationsAllowSystemNotifications] = deviceData.pushNotificationsAllowSystemNotifications
        this[pushNotificationsAllowIndividualOffers] = deviceData.pushNotificationsAllowIndividualOffers
        this[pushNotificationsAllowAccountAlarm] = deviceData.pushNotificationsAllowAccountAlarm
        this[pushNotificationsAllowNotificationBadge] = deviceData.pushNotificationsAllowNotificationBadge
        this[pushNotificationsLastPingNotificationReceived] = LocalDateTime.parse(deviceData.pushNotificationsLastPingNotificationReceived, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[metricsFullAppStarts] = deviceData.metricsFullAppStarts
        this[metricsFullAppStartsMs] = deviceData.metricsFullAppStartsMs
        this[metricsSubsequentAppStarts] = deviceData.metricsSubsequentAppStarts
        this[metricsSubsequentAppStartsMs] = deviceData.metricsSubsequentAppStartsMs
        this[deviceLastConnect] = LocalDateTime.parse(deviceData.deviceLastConnect, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        this[deviceLastCommit] = LocalDateTime.parse(deviceData.deviceLastCommit, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    fun DeviceDataTable.updateOrInsert(deviceData: DeviceDataModel): Int {
        val updatedRows = update({ deviceId eq deviceData.deviceId }) {
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
