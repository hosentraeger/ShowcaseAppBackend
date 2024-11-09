package de.fsiebecke.ShowcaseAppBackend.plugins

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PushNotificationBuilder {
    suspend fun sendPushNotification ( pushNotificationData: PushNotificationData) : Boolean {
        val logger: Logger = LoggerFactory.getLogger("PushNotificationBuilder")
        val pushNotificationPayloadBase = PushNotificationPayload (
             blz=pushNotificationData.pushFormBlz,
             obv=pushNotificationData.pushFormObv,
             title=pushNotificationData.pushFormTitle,
             body=pushNotificationData.pushFormBody
        )

        val pushNotificationPayload = when ( pushNotificationData.structure ) {
            "IAM" -> {
                pushNotificationPayloadBase.copy(
                    iam = IamPayload (
                        contentId=pushNotificationData.contentIdSelect,
                        notificationImage=pushNotificationData.notificationImageSelect,
                        overlayImage=pushNotificationData.overlayImageSelect,
                        eventId=pushNotificationData.eventId,
                        expectFeedbackIfDisplayed=false,
                        expectFeedbackIfHit=false
                    )
                )
            }
            else -> null
        }

        val jsonPayload = Json.encodeToString(pushNotificationPayload)
        val encodedPayload = Base64.getEncoder().encodeToString(jsonPayload.toByteArray())
        logger.info(encodedPayload)

        // Push Notification Logik
        val success = PushNotificationService.sendPushNotification(
            fcmToken = pushNotificationData.fcmToken,
            title = if ( pushNotificationData.pushFormPayloadAsNotification ) pushNotificationData.pushFormTitle else "",
            body = if ( pushNotificationData.pushFormPayloadAsNotification ) pushNotificationData.pushFormBody else "",
            data = mapOf( "customKey1" to encodedPayload )
        )
        return success
    }
}
