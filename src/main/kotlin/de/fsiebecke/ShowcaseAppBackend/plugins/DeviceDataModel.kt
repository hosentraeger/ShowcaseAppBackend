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
