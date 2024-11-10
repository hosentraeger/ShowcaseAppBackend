package de.fsiebecke.ShowcaseAppBackend.plugins

import kotlinx.serialization.Serializable

@Serializable
data class DeviceDataModel(
    val deviceId: String,
    val systemSfStage: SfStage,
    val systemMkaStage: MkaStage,
    val systemMkaLine: Int,
    val appVersion: String,
    val appId: Int,
    val appLanguage: String,
    val appTheme: AppTheme,
    val appMaxSessionDuration: Int,
    val appAutoUpdateAccounts: Boolean,
    val appAccountSorting: Int,
    val userName: String,
    val userFcmToken: String,
    val userDateTimeInitialLogin: String,
    val userDateTimeLastLogin: String,
    val userAgreeCollectStatistics: Boolean,
    val subscriptionWero: Boolean,
    val subscriptionKwitt: Boolean,
    val subscriptionDiamond: Boolean,
    val subscriptionAloha: Boolean,
    val subscriptionBudgetBook: Boolean,
    val pushNotificationsAllowSystemNotifications: Boolean,
    val pushNotificationsAllowIndividualOffers: Boolean,
    val pushNotificationsAllowAccountAlarm: Boolean,
    val pushNotificationsAllowNotificationBadge: Boolean,
    val pushNotificationsLastPingNotificationReceived: String,
    val metricsFullAppStarts: Int,
    val metricsFullAppStartsMs: Int,
    val metricsSubsequentAppStarts: Int,
    val metricsSubsequentAppStartsMs: Int,
    val deviceLastConnect: String,
    val deviceLastCommit: String
)

@Serializable
data class DeviceData(
    var userData : UserData,
    var deviceReport : DeviceReport? = null,
    var featureSettings : FeatureSettings? = null,
    var appUsageStatistics : AppUsageStatistics? = null,
    var appMetrics : AppMetrics? = null,
    var deviceMetaData: DeviceMetaData? = null
)

@Serializable
data class UserData(
    var push_id: String = "<not assigned>",
    var login_id: String = "<nobody>",
    var last_login: String = "1971-01-01T00:00:00Z",
)

@Serializable
data class DeviceReport(
    var model: String? = null,
    var osVersion: String? = null,
    var appVariant: String? = null,
    var appVersion: String? = null,
    var wero: Boolean? = null,
    var kwitt: Boolean? = null,
    var applePay: Boolean? = null,
    var aloha: Boolean? = null,
    var systemLanguage: String? = null,
    var budgetBook: Boolean? = null,
    var pushNotificationSettings : PushNotificationSettings? = null,
    var modified: Boolean = false
)

@Serializable
data class PushNotificationSettings(
    var system : Boolean = false,
    var individualOffers : Boolean = false,
    var accountAlarm : Boolean = false,
    var notificationBadge : Boolean = false,
    var lastPingNotificiationReceived : String? = null
)

@Serializable
data class FeatureSettings(
    var appLanguage: String? = null,
    var theme: String? = null,
    var maxSessionDuration: Int? = null,
    var autoUpateBalance: Boolean? = null,
    var accountSorting: String? = null,
    var modified: Boolean = false
)

@Serializable
data class AppUsageStatistics(
    var institutionsAndAccounts: InstitutionsAndAccounts? = null,
    var featuresRequiringAttention: List<String>? = null,
    var anonymized: Boolean = false,
    var modified: Boolean = false
)

@Serializable
data class InstitutionsAndAccounts(
    var mkaAccounts: Int = 0,
    var savingBanks: Int = 0,
    var savingBanksAccounts: Int = 0,
    var mbfAccounts: Int = 0,
    var otherAccounts: Int = 0
)

@Serializable
data class AppMetrics(
    var fullAppStartsSinceLastCommit: Int = 0,
    var fullAppStartsMsSinceLastCommit: Int = 0,
    var subsequentAppStartsSinceLastCommit: Int = 0,
    var subsequentAppStartsMsSinceLastCommit: Int = 0,
    var anonymized: Boolean = false
)

@Serializable
data class DeviceMetaData(
    var deviceId: String,
    var lastConnect: String,
    var lastCommitIp: String,
    var lastCommit: String,
    var deviceDataCommitted: Boolean
)

@Serializable
data class DeviceDataComputedValues(
    var appStartsTotal: Int,
    var appStartsThisMonth: Int,
    var appStartsLastMonth: Int,
    var averageUpdateMsFirstThisMonth: Int,
    var averageUpdateMsFirstLastMonth: Int,
    var averageUpdateMsSubsequentLastMonth: Int,
    var initialConnect: String,
    var initialConnectIp: String
)

@Serializable
data class DeviceFeatures(
    var modifiedBackend: Boolean,
    var dynatrace: Boolean,
    var acceptTermsConditions: Boolean,
    var reviewPersonalData: Boolean,
    var incomeExpenseWidgetVariant: String,
    var click2Credit: Boolean,
    var click2CreditVariant: String,
    var aroundTheProperty: Boolean,
    var privateBanking: Boolean,
    var moneyBoxFieldTesting: Boolean,
    var mergerAssistant: Boolean
)
