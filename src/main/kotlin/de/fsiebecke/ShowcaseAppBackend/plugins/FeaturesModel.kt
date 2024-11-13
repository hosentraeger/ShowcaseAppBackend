package de.fsiebecke.ShowcaseAppBackend.plugins
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class FeaturesModel (
    val dataModelVersion: Int,
    val deviceId: String,
    @Contextual val lastLoginDateTime: LocalDateTime? = null,
    val incomeExpenseWidgetVariant: Boolean? = null,
    val offerAroundTheProperty: Boolean? = null,
    val offerClick2Credit: Boolean? = null,
    val offerMoneyBoxFieldTesting: Boolean? = null,
    val offerPrivateBanking: Boolean? = null,
    val requireReviewPersonalData: Boolean? = null,
    val requireTermsConditionsAgreement: Boolean? = null,
    val sendDynatraceBeacons: Boolean? = null,
    val realtimeTransfer: Boolean? = null,
    val httpTraffic8443: Boolean? = null,
    val sCashback: Boolean? = null,
    val review: Boolean? = null
)
