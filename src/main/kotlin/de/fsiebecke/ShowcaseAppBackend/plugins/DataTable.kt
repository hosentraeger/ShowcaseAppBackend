package de.fsiebecke.ShowcaseAppBackend.plugins

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.time.LocalDateTime

// Tabelle für DeviceData
object DataTable : Table("showcase_data") {
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
    val incomeExpenseWidgetVariant = bool("income_expense_widget_variant").nullable()
    val offerAroundTheProperty = bool("offer_around_the_property").nullable()
    val offerClick2Credit = bool("offer_click2_credit").nullable()
    val offerMergerAssistant = bool("offer_merger_assistant").nullable()
    val offerMoneyBoxFieldTesting = bool("offer_money_box_field_testing").nullable()
    val offerPrivateBanking = bool("offer_private_banking").nullable()
    val requireReviewPersonalData = bool("require_review_personal_data").nullable()
    val requireTermsConditionsAgreement = bool("require_terms_conditions_agreement").nullable()
    val sendDynatraceBeacons = bool("send_dynatrace_beacons").nullable()
    val allowAccountAlarmNotifications = bool("allow_account_alarm_notifications").nullable()
    val allowIndividualOffersNotifications = bool("allow_individual_offers_notifications").nullable()
    val allowNotificationBadge = bool("allow_notification_badge").nullable()
    val allowSystemNotifications = bool("allow_system_notifications").nullable()
    val lastPingNotificationReceivedDateTime = datetime("last_ping_notification_received_date_time").nullable()
    val pushToken = varchar("push_token", 255).nullable()
    val fullAppStartsLastMonth = integer("full_app_starts_last_month").nullable()
    val fullAppStartsMsLastMonth = integer("full_app_starts_ms_last_month").nullable()
    val fullAppStartsMsThisMonth = integer("full_app_starts_ms_this_month").nullable()
    val fullAppStartsMsTotal = integer("full_app_starts_ms_total").nullable()
    val fullAppStartsThisMonth = integer("full_app_starts_this_month").nullable()
    val fullAppStartsTotal = integer("full_app_starts_total").nullable()
    val initialLoginDateTime = datetime("initial_login_date_time").nullable()
    val initialLoginIp = varchar("initial_login_ip", 255).nullable()
    val lastCommitDateTime = datetime("last_commit_date_time").nullable()
    val lastCommitIp = varchar("last_commit_ip", 255).nullable()
    val lastLoginDateTime = datetime("last_login_date_time").nullable()
    val numberOfMbfAccounts = integer("number_of_mbf_accounts").nullable()
    val numberOfMkaAccounts = integer("number_of_mka_accounts").nullable()
    val numberOfOtherAccounts = integer("number_of_other_accounts").nullable()
    val numberOfSavingBanks = integer("number_of_saving_banks").nullable()
    val numberOfSavingBanksAccounts = integer("number_of_saving_banks_accounts").nullable()
    val subsequentAppStartsLastMonth = integer("subsequent_app_starts_last_month").nullable()
    val subsequentAppStartsMsLastMonth = integer("subsequent_app_starts_ms_last_month").nullable()
    val subsequentAppStartsMsThisMonth = integer("subsequent_app_starts_ms_this_month").nullable()
    val subsequentAppStartsMsTotal = integer("subsequent_app_starts_ms_total").nullable()
    val subsequentAppStartsThisMonth = integer("subsequent_app_starts_this_month").nullable()
    val subsequentAppStartsTotal = integer("subsequent_app_starts_total").nullable()
    val featuresRequiringAttention = enumerationByName("features_requiring_attention", 50, UsageFeatureEnum::class).nullable()
    val subscribedToAloha = bool("subscribed_to_aloha").nullable()
    val subscribedToBudgetBook = bool("subscribed_to_budget_book").nullable()
    val subscribedToDiamond = bool("subscribed_to_diamond").nullable()
    val subscribedToKwitt = bool("subscribed_to_kwitt").nullable()
    val subscribedToWero = bool("subscribed_to_wero").nullable()
    val minRecommendedVersion = integer("min_recommended_version").nullable()
    val minRequiredVersion = integer("min_required_version").nullable()
    val anonymizePersonalData = bool("anonymize_personal_data").nullable()
    val primaryUserName = varchar("primary_user_name", 255).nullable()

    private fun InsertStatement<*>.fromDataModel(data: DataModel) {
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
        this[incomeExpenseWidgetVariant] = data.incomeExpenseWidgetVariant
        this[offerAroundTheProperty] = data.offerAroundTheProperty
        this[offerClick2Credit] = data.offerClick2Credit
        this[offerMergerAssistant] = data.offerMergerAssistant
        this[offerMoneyBoxFieldTesting] = data.offerMoneyBoxFieldTesting
        this[offerPrivateBanking] = data.offerPrivateBanking
        this[requireReviewPersonalData] = data.requireReviewPersonalData
        this[requireTermsConditionsAgreement] = data.requireTermsConditionsAgreement
        this[sendDynatraceBeacons] = data.sendDynatraceBeacons
        this[allowAccountAlarmNotifications] = data.allowAccountAlarmNotifications
        this[allowIndividualOffersNotifications] = data.allowIndividualOffersNotifications
        this[allowNotificationBadge] = data.allowNotificationBadge
        this[allowSystemNotifications] = data.allowSystemNotifications
        this[lastPingNotificationReceivedDateTime] = data.lastPingNotificationReceivedDateTime
        this[pushToken] = data.pushToken
        this[lastCommitDateTime] = data.lastCommitDateTime
        this[lastLoginDateTime] = data.lastLoginDateTime
        this[numberOfMbfAccounts] = data.numberOfMbfAccounts
        this[numberOfMkaAccounts] = data.numberOfMkaAccounts
        this[numberOfOtherAccounts] = data.numberOfOtherAccounts
        this[numberOfSavingBanks] = data.numberOfSavingBanks
        this[numberOfSavingBanksAccounts] = data.numberOfSavingBanksAccounts
        this[featuresRequiringAttention] = data.featuresRequiringAttention
        this[subscribedToAloha] = data.subscribedToAloha
        this[subscribedToBudgetBook] = data.subscribedToBudgetBook
        this[subscribedToDiamond] = data.subscribedToDiamond
        this[subscribedToKwitt] = data.subscribedToKwitt
        this[subscribedToWero] = data.subscribedToWero
        this[minRecommendedVersion] = data.minRecommendedVersion
        this[minRequiredVersion] = data.minRequiredVersion
        this[anonymizePersonalData] = data.anonymizePersonalData
        this[primaryUserName] = data.primaryUserName

        this[fullAppStartsLastMonth] = 0
        this[fullAppStartsMsLastMonth] = 0
        this[fullAppStartsMsThisMonth] = 0
        this[fullAppStartsMsTotal] = 0
        this[fullAppStartsThisMonth] = 0
        this[fullAppStartsTotal] = 0
        this[subsequentAppStartsLastMonth] = 0
        this[subsequentAppStartsMsLastMonth] = 0
        this[subsequentAppStartsMsThisMonth] = 0
        this[subsequentAppStartsMsTotal] = 0
        this[subsequentAppStartsThisMonth] = 0
        this[subsequentAppStartsTotal] = 0
    }

    private fun UpdateStatement.fromDataModel(data: DataModel) {
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
        this[incomeExpenseWidgetVariant] = data.incomeExpenseWidgetVariant
        this[offerAroundTheProperty] = data.offerAroundTheProperty
        this[offerClick2Credit] = data.offerClick2Credit
        this[offerMergerAssistant] = data.offerMergerAssistant
        this[offerMoneyBoxFieldTesting] = data.offerMoneyBoxFieldTesting
        this[offerPrivateBanking] = data.offerPrivateBanking
        this[requireReviewPersonalData] = data.requireReviewPersonalData
        this[requireTermsConditionsAgreement] = data.requireTermsConditionsAgreement
        this[sendDynatraceBeacons] = data.sendDynatraceBeacons
        this[allowAccountAlarmNotifications] = data.allowAccountAlarmNotifications
        this[allowIndividualOffersNotifications] = data.allowIndividualOffersNotifications
        this[allowNotificationBadge] = data.allowNotificationBadge
        this[allowSystemNotifications] = data.allowSystemNotifications
        this[lastPingNotificationReceivedDateTime] = data.lastPingNotificationReceivedDateTime
        this[pushToken] = data.pushToken
        this[lastCommitDateTime] = data.lastCommitDateTime
        this[lastLoginDateTime] = data.lastLoginDateTime
        this[numberOfMbfAccounts] = data.numberOfMbfAccounts
        this[numberOfMkaAccounts] = data.numberOfMkaAccounts
        this[numberOfOtherAccounts] = data.numberOfOtherAccounts
        this[numberOfSavingBanks] = data.numberOfSavingBanks
        this[numberOfSavingBanksAccounts] = data.numberOfSavingBanksAccounts
        this[featuresRequiringAttention] = data.featuresRequiringAttention
        this[subscribedToAloha] = data.subscribedToAloha
        this[subscribedToBudgetBook] = data.subscribedToBudgetBook
        this[subscribedToDiamond] = data.subscribedToDiamond
        this[subscribedToKwitt] = data.subscribedToKwitt
        this[subscribedToWero] = data.subscribedToWero
        this[minRecommendedVersion] = data.minRecommendedVersion
        this[minRequiredVersion] = data.minRequiredVersion
        this[anonymizePersonalData] = data.anonymizePersonalData
        this[primaryUserName] = data.primaryUserName

        /*
                this[lastCommitIp] = data.lastCommitIp
         */
    }

    fun DeviceDataTable.updateOrInsert(data: DataModel, loginDateTime: LocalDateTime, loginIp: String): Int {
        val row = selectAll().where { deviceId eq data.deviceId }.singleOrNull()
        // Berechne die neuen Werte, falls ein Datensatz existiert
        val newFullAppStartsThisMonth =
            row?.get(fullAppStartsThisMonth) ?: (0 + (data.fullAppStartsSinceLastCommit ?: 0))
        val newSubsequentAppStartsThisMonth =
            row?.get(subsequentAppStartsThisMonth) ?: (0 + (data.subsequentAppStartsSinceLastCommit ?: 0))
        val newFullAppStartsMsThisMonth =
            row?.get(fullAppStartsMsThisMonth) ?: (0 + (data.fullAppStartsMsSinceLastCommit ?: 0))
        val newSubsequentAppStartsMsThisMonth =
            row?.get(subsequentAppStartsMsThisMonth) ?: (0 + (data.subsequentAppStartsMsSinceLastCommit ?: 0))
        val newFullAppStartsTotal =
            row?.get(fullAppStartsTotal) ?: (0 + (data.fullAppStartsSinceLastCommit ?: 0))
        val newSubsequentAppStartsTotal =
            row?.get(subsequentAppStartsTotal) ?: (0 + (data.subsequentAppStartsSinceLastCommit ?: 0))
        val newFullAppStartsMsTotal =
            row?.get(fullAppStartsMsTotal) ?: (0 + (data.fullAppStartsMsSinceLastCommit ?: 0))
        val newSubsequentAppStartsMsTotal =
            row?.get(subsequentAppStartsMsTotal) ?: (0 + (data.subsequentAppStartsMsSinceLastCommit ?: 0))

        // Update-Versuch
        val updatedRows = update({ deviceId eq data.deviceId }) {
            it[fullAppStartsThisMonth] = newFullAppStartsThisMonth
            it[subsequentAppStartsThisMonth] = newSubsequentAppStartsThisMonth
            it[fullAppStartsMsThisMonth] = newFullAppStartsMsThisMonth
            it[subsequentAppStartsMsThisMonth] = newSubsequentAppStartsMsThisMonth
            it[fullAppStartsTotal] = newFullAppStartsTotal
            it[subsequentAppStartsTotal] = newSubsequentAppStartsTotal
            it[fullAppStartsMsTotal] = newFullAppStartsMsTotal
            it[subsequentAppStartsMsTotal] = newSubsequentAppStartsMsTotal
            it[lastCommitIp] = loginIp
            it.fromDataModel(data)
        }
        return if (updatedRows > 0) {
            updatedRows // Rückgabe der Anzahl der aktualisierten Zeilen
        } else {
            insert {
                it[initialLoginDateTime] = loginDateTime
                it[initialLoginIp] = loginIp
                it[lastCommitIp] = loginIp
                it.fromDataModel(data) // für InsertStatement
            }
            1 // Rückgabe von 1, um anzuzeigen, dass ein Eintrag eingefügt wurde
        }
    }
}
