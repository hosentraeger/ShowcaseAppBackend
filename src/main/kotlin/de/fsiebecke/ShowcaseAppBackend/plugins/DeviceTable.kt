package de.fsiebecke.ShowcaseAppBackend.plugins

import de.fsiebecke.ShowcaseAppBackend.CURRENT_DATA_MODEL_VERSION
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

// Tabelle für DeviceData
object DeviceTable : Table("showcase_device_data") {
    val deviceId = varchar("deviceId", 255).uniqueIndex() // Primary Key
    val accountSorting = enumerationByName("account_sorting", 50, AccountSortingEnum::class).nullable()
    val autoUpdateBalance = bool("auto_update_balance").nullable()
    val appId = integer("app_id").nullable()
    val appLanguage = varchar("app_language", 255).nullable()
    val maxSessionDuration = integer("max_session_duration").nullable()
    val appTheme = enumerationByName("app_theme", 50, AppThemeEnum::class).nullable()
    val appVariant = varchar("app_variant", 255).nullable()
    val appVersion = integer("app_version").nullable()
    val deviceModel = varchar("device_model", 255).nullable()
    val deviceTheme = enumerationByName("device_theme", 50, AppThemeEnum::class).nullable()
    val osVersion = varchar("os_version", 255).nullable()
    val physicalDeviceId = varchar("physical_device_id", 255).nullable()
    val systemLanguage = varchar("system_language", 255).nullable()
    val allowAccountAlarmNotifications = bool("allow_account_alarm_notifications").nullable()
    val allowIndividualOffersNotifications = bool("allow_individual_offers_notifications").nullable()
    val allowNotificationBadge = bool("allow_notification_badge").nullable()
    val allowSystemNotifications = bool("allow_system_notifications").nullable()
    val lastPingNotificationReceivedDateTime = datetime("last_ping_notification_received_date_time").nullable()
    val pushToken = varchar("push_token", 255).nullable()
    val initialLoginDateTime = datetime("initial_login_date_time").nullable()
    val initialLoginIp = varchar("initial_login_ip", 255).nullable()
    val lastLoginDateTime = datetime("last_login_date_time").nullable()
    val lastCommitIp = varchar("last_commit_ip", 255).nullable()
    val numberOfMbfAccounts = integer("number_of_mbf_accounts").nullable()
    val numberOfMkaAccounts = integer("number_of_mka_accounts").nullable()
    val numberOfOtherAccounts = integer("number_of_other_accounts").nullable()
    val numberOfSavingBanks = integer("number_of_saving_banks").nullable()
    val numberOfSavingBanksAccounts = integer("number_of_saving_banks_accounts").nullable()
    val subscribedToAloha = bool("subscribed_to_aloha").nullable()
    val subscribedToBudgetBook = bool("subscribed_to_budget_book").nullable()
    val subscribedToDiamond = bool("subscribed_to_diamond").nullable()
    val subscribedToKwitt = bool("subscribed_to_kwitt").nullable()
    val subscribedToWero = bool("subscribed_to_wero").nullable()
    val anonymizePersonalData = bool("anonymize_personal_data").nullable()
    val primaryUserName = varchar("primary_user_name", 255).nullable()
    val appLoginMode = enumerationByName("app_login_mode", 50, LoginModeEnum::class).nullable()
    val allowFirebaseTracking = bool("allow_firebase_tracking").nullable()
    val quickAccessButtons = varchar("quick_access_buttons", 255).nullable()
    val otherRedApps = varchar("other_red_apps", 255).nullable()
    val featuresRequiringAttention = varchar("features_requiring_attention", 255).nullable()
    val frequentlyUsedFeatures = varchar("frequently_used_features", 255).nullable()

    private fun InsertStatement<*>.fromModel(data: DeviceDataModel) {
        this[deviceId] = data.deviceId
        this[accountSorting] = data.accountSorting
        this[autoUpdateBalance] = data.autoUpdateBalance
        this[appId] = data.appId
        this[appLanguage] = data.appLanguage
        this[maxSessionDuration] = data.maxSessionDuration
        this[appTheme] = data.appTheme
        this[appVariant] = data.appVariant
        this[appVersion] = data.appVersion
        this[deviceModel] = data.deviceModel
        this[deviceTheme] = data.deviceTheme
        this[osVersion] = data.osVersion
        this[physicalDeviceId] = data.physicalDeviceId
        this[systemLanguage] = data.systemLanguage
        this[allowAccountAlarmNotifications] = data.allowAccountAlarmNotifications
        this[allowIndividualOffersNotifications] = data.allowIndividualOffersNotifications
        this[allowNotificationBadge] = data.allowNotificationBadge
        this[allowSystemNotifications] = data.allowSystemNotifications
        this[lastPingNotificationReceivedDateTime] = data.lastPingNotificationReceivedDateTime
        this[pushToken] = data.pushToken
        this[lastLoginDateTime] = data.lastLoginDateTime
        this[numberOfMbfAccounts] = data.numberOfMbfAccounts
        this[numberOfMkaAccounts] = data.numberOfMkaAccounts
        this[numberOfOtherAccounts] = data.numberOfOtherAccounts
        this[numberOfSavingBanks] = data.numberOfSavingBanks
        this[numberOfSavingBanksAccounts] = data.numberOfSavingBanksAccounts
        this[subscribedToAloha] = data.subscribedToAloha
        this[subscribedToBudgetBook] = data.subscribedToBudgetBook
        this[subscribedToDiamond] = data.subscribedToDiamond
        this[subscribedToKwitt] = data.subscribedToKwitt
        this[subscribedToWero] = data.subscribedToWero
        this[anonymizePersonalData] = data.anonymizePersonalData
        this[primaryUserName] = data.primaryUserName
        this[appLoginMode] = data.appLoginMode
        this[allowFirebaseTracking] = data.allowFirebaseTracking
        this[featuresRequiringAttention] = data.featuresRequiringAttention?.let {
            Json.encodeToString(it)
        }
        this[frequentlyUsedFeatures] = data.frequentlyUsedFeatures?.let {
            Json.encodeToString(it)
        }
        this[quickAccessButtons] = data.quickAccessButtons?.let {
            Json.encodeToString(it)
        }
        this[otherRedApps] = data.otherRedApps?.let {
            Json.encodeToString(it)
        }

        this[initialLoginDateTime] = data.lastLoginDateTime
    }

    private fun UpdateStatement.fromModel(data: DeviceDataModel) {
        this[deviceId] = data.deviceId
        this[accountSorting] = data.accountSorting
        this[autoUpdateBalance] = data.autoUpdateBalance
        this[appId] = data.appId
        this[appLanguage] = data.appLanguage
        this[maxSessionDuration] = data.maxSessionDuration
        this[appTheme] = data.appTheme
        this[appVariant] = data.appVariant
        this[appVersion] = data.appVersion
        this[deviceModel] = data.deviceModel
        this[deviceTheme] = data.deviceTheme
        this[osVersion] = data.osVersion
        this[physicalDeviceId] = data.physicalDeviceId
        this[systemLanguage] = data.systemLanguage
        this[allowAccountAlarmNotifications] = data.allowAccountAlarmNotifications
        this[allowIndividualOffersNotifications] = data.allowIndividualOffersNotifications
        this[allowNotificationBadge] = data.allowNotificationBadge
        this[allowSystemNotifications] = data.allowSystemNotifications
        this[lastPingNotificationReceivedDateTime] = data.lastPingNotificationReceivedDateTime
        this[pushToken] = data.pushToken
        this[lastLoginDateTime] = data.lastLoginDateTime
        this[numberOfMbfAccounts] = data.numberOfMbfAccounts
        this[numberOfMkaAccounts] = data.numberOfMkaAccounts
        this[numberOfOtherAccounts] = data.numberOfOtherAccounts
        this[numberOfSavingBanks] = data.numberOfSavingBanks
        this[numberOfSavingBanksAccounts] = data.numberOfSavingBanksAccounts
        this[subscribedToAloha] = data.subscribedToAloha
        this[subscribedToBudgetBook] = data.subscribedToBudgetBook
        this[subscribedToDiamond] = data.subscribedToDiamond
        this[subscribedToKwitt] = data.subscribedToKwitt
        this[subscribedToWero] = data.subscribedToWero
        this[anonymizePersonalData] = data.anonymizePersonalData
        this[primaryUserName] = data.primaryUserName
        this[appLoginMode] = data.appLoginMode
        this[allowFirebaseTracking] = data.allowFirebaseTracking
        this[featuresRequiringAttention] = data.featuresRequiringAttention?.let {
            Json.encodeToString(it)
        }
        this[frequentlyUsedFeatures] = data.frequentlyUsedFeatures?.let {
            Json.encodeToString(it)
        }
        this[quickAccessButtons] = data.quickAccessButtons?.let {
            Json.encodeToString(it)
        }
        this[otherRedApps] = data.otherRedApps?.let {
            Json.encodeToString(it)
        }
    }

    fun updateOrInsert(data: DeviceDataModel, clientIp: String): Int {
        // Update-Versuch
        val updatedRows = update({ deviceId eq data.deviceId }) {
            it[lastCommitIp] = clientIp
            it.fromModel(data)
        }
        return if (updatedRows > 0) {
            updatedRows // Rückgabe der Anzahl der aktualisierten Zeilen
        } else {
            insert {
                it[initialLoginIp] = clientIp
                it[lastCommitIp] = clientIp
                it.fromModel(data) // für InsertStatement
            }
            1 // Rückgabe von 1, um anzuzeigen, dass ein Eintrag eingefügt wurde
        }
    }

    fun toModel(row: ResultRow): DeviceDataModel {
        return DeviceDataModel(
            dataModelVersion = CURRENT_DATA_MODEL_VERSION,
            deviceId = row[deviceId],
            primaryUserName = row[primaryUserName]!!,
            accountSorting = row[accountSorting],
            autoUpdateBalance = row[autoUpdateBalance],
            appId = row[appId],
            appLanguage = row[appLanguage],
            maxSessionDuration = row[maxSessionDuration],
            appTheme = row[appTheme],
            appVariant = row[appVariant],
            appVersion = row[appVersion],
            deviceModel = row[deviceModel],
            deviceTheme = row[deviceTheme],
            osVersion = row[osVersion],
            physicalDeviceId = row[physicalDeviceId],
            systemLanguage = row[systemLanguage],
            allowAccountAlarmNotifications = row[allowAccountAlarmNotifications],
            allowIndividualOffersNotifications = row[allowIndividualOffersNotifications],
            allowNotificationBadge = row[allowNotificationBadge],
            allowSystemNotifications = row[allowSystemNotifications],
            lastPingNotificationReceivedDateTime = row[lastPingNotificationReceivedDateTime],
            pushToken = row[pushToken],
            lastLoginDateTime = row[lastLoginDateTime],
            numberOfMbfAccounts = row[numberOfMbfAccounts],
            numberOfMkaAccounts = row[numberOfMkaAccounts],
            numberOfOtherAccounts = row[numberOfOtherAccounts],
            numberOfSavingBanks = row[numberOfSavingBanks],
            numberOfSavingBanksAccounts = row[numberOfSavingBanksAccounts],
            subscribedToAloha = row[subscribedToAloha],
            subscribedToBudgetBook = row[subscribedToBudgetBook],
            subscribedToDiamond = row[subscribedToDiamond],
            subscribedToKwitt = row[subscribedToKwitt],
            subscribedToWero = row[subscribedToWero],
            anonymizePersonalData = row[anonymizePersonalData],

            frequentlyUsedFeatures = row[frequentlyUsedFeatures]?.let { jsonString ->
                Json.decodeFromString<List<UsageFeatureEnum>>(jsonString)
            },
            featuresRequiringAttention = row[featuresRequiringAttention]?.let { jsonString ->
                Json.decodeFromString<List<UsageFeatureEnum>>(jsonString)
            },
            quickAccessButtons = row[quickAccessButtons]?.let { jsonString ->
                Json.decodeFromString<List<QuickAccessButtonsEnum>>(jsonString)
            }
        )
    }

    // Hilfsfunktion zum Abrufen aller Geräte
    fun selectAllRecords(): List<ResultRow> {
        return this.selectAll().toList() // Liste von ResultRow zurückgeben
    }
}
