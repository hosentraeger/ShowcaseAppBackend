package de.fsiebecke.ShowcaseAppBackend.plugins

import kotlinx.serialization.Serializable

@Serializable
data class PushNotificationData(
    val deviceId: String,
    val fcmToken: String,
    val appId: String = "",
    val pushFormPayloadAsNotification: Boolean = false,
    val balanceIban: String = "",
    val balanceValue: String = "",
    val contentIdSelect: String = "",
    val individualWebviewPath: String = "",
    val mailboxUnreadCount: String = "",
    val notificationImageSelect: String = "",
    val overlayImageSelect: String = "",
    val expectFeedbackIfDisplayed: Boolean? = false,
    val expectFeedbackIfHit: Boolean? = false,
    val eventId: String = "",
    val pushFormBlz: String = "",
    val pushFormBody: String = "",
    val pushFormObv: String = "",
    val pushFormTitle: String = "",
    val structure: String = "",
    val updateVersionFrom: String = "",
    val webviewUrlSelect: String = ""
)

@Serializable
data class PushNotificationPayload(
    val blz: String? = null,
    val obv: String? = null,
    val title: String? = null,
    val body: String? = null,
    val webview: WebviewPayload? = null,
    val iam: IamPayload? = null,
    val mailbox: MailboxPayload? = null,
    val balance: BalancePayload? = null,
    val update: UpdatePayload? = null,
    val ping: PingPayload? = null
)

@Serializable
data class WebviewPayload(
    val path: String
)

@Serializable
data class IamPayload(
    val contentId: String,
    val notificationImage: String? = null,
    val overlayImage : String? = null,
    val eventId : String? = null,
    val expectFeedbackIfDisplayed: Boolean? = false,
    val expectFeedbackIfHit: Boolean? = false
)

@Serializable
data class MailboxPayload(
    val count: Int
)

@Serializable
data class BalancePayload(
    val iban: String,
    val balance: String
)

@Serializable
data class UpdatePayload(
    val fromVersion: String
)

@Serializable
class PingPayload
