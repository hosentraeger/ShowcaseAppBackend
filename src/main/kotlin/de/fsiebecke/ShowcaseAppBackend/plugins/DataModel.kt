package de.fsiebecke.ShowcaseAppBackend.plugins
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class DataModel(
    val deviceId: String,
    val primaryUserName: String,
    val accountSorting: AccountSortingEnum? = null,
    val autoUpdateBalance: Boolean? = null,
    val appId: Int? = null,
    val appLanguage: String? = null,
    val maxSessionDuration: Int? = null,
    val appTheme: AppThemeEnum? = null,
    val appVariant: String? = null,
    val appVersion: Int? = null,
    val appLoginMode: LoginModeEnum? = null,
    val allowFirebaseTracking: Boolean? = null,
    val deviceModel: String? = null,
    val deviceTheme: AppThemeEnum? = null,
    val otherRedApps: List<String?>? = null,
    val osVersion: String? = null,
    val physicalDeviceId: String? = null,
    val systemLanguage: String? = null,
    val incomeExpenseWidgetVariant: Boolean? = null,
    val offerAroundTheProperty: Boolean? = null,
    val offerClick2Credit: Boolean? = null,
    val offerMoneyBoxFieldTesting: Boolean? = null,
    val offerPrivateBanking: Boolean? = null,
    val requireReviewPersonalData: Boolean? = null,
    val requireTermsConditionsAgreement: Boolean? = null,
    val sendDynatraceBeacons: Boolean? = null,
    val allowAccountAlarmNotifications: Boolean? = null,
    val allowIndividualOffersNotifications: Boolean? = null,
    val allowNotificationBadge: Boolean? = null,
    val allowSystemNotifications: Boolean? = null,
    @Contextual val lastPingNotificationReceivedDateTime: LocalDateTime? = null,
    val pushToken: String? = null,
    val fullAppStartsMsSinceLastCommit: Int? = null,
    val fullAppStartsSinceLastCommit: Int? = null,
    @Contextual val lastLoginDateTime: LocalDateTime? = null,
    val numberOfMbfAccounts: Int? = null,
    val numberOfMkaAccounts: Int? = null,
    val numberOfOtherAccounts: Int? = null,
    val numberOfSavingBanks: Int? = null,
    val numberOfSavingBanksAccounts: Int? = null,
    val subsequentAppStartsMsSinceLastCommit: Int? = null,
    val subsequentAppStartsSinceLastCommit: Int? = null,
    val frequentlyUsedFeatures: List<UsageFeatureEnum>? = null,
    val featuresRequiringAttention: List<UsageFeatureEnum>? = null,
    val subscribedToAloha: Boolean? = null,
    val subscribedToBudgetBook: Boolean? = null,
    val subscribedToDiamond: Boolean? = null,
    val subscribedToKwitt: Boolean? = null,
    val subscribedToWero: Boolean? = null,
    val minRecommendedVersion: Int? = null,
    val minRequiredVersion: Int? = null,
    val anonymizePersonalData: Boolean? = null,
    val quickAccessButtons: List<QuickAccessButtonsEnum>? = null
)

@Serializable
data class FeaturesModel (
    val realtimeTransfer: Boolean? = null,
    val httpTraffic8443: Boolean? = null,
    val sCashback: Boolean? = null,
    val review: Boolean? = null
)

@Serializable
data class AppstartModel (
    val softKillVersions: List<String?>? = null,
    val hardKillVersions: List<String?>? = null,
    val offerMergerAssistant: Boolean? = null
)
