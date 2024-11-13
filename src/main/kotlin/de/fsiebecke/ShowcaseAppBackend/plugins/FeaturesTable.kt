package de.fsiebecke.ShowcaseAppBackend.plugins

import de.fsiebecke.ShowcaseAppBackend.CURRENT_DATA_MODEL_VERSION
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

// Tabelle für DeviceData
object FeaturesTable : Table("showcase_features") {
    val deviceId = varchar("deviceId", 255).uniqueIndex() // Primary Key
    val lastCommitIp = varchar("last_commit_ip", 255).nullable()
    val incomeExpenseWidgetVariant = bool("income_expense_widget_variant").nullable()
    val offerAroundTheProperty = bool("offer_around_the_property").nullable()
    val offerClick2Credit = bool("offer_click2_credit").nullable()
    val offerMoneyBoxFieldTesting = bool("offer_money_box_field_testing").nullable()
    val offerPrivateBanking = bool("offer_private_banking").nullable()
    val requireReviewPersonalData = bool("require_review_personal_data").nullable()
    val requireTermsConditionsAgreement = bool("require_terms_conditions_agreement").nullable()
    val sendDynatraceBeacons = bool("send_dynatrace_beacons").nullable()

    private fun InsertStatement<*>.fromModel(data: FeaturesModel) {
        this[incomeExpenseWidgetVariant] = data.incomeExpenseWidgetVariant
        this[offerAroundTheProperty] = data.offerAroundTheProperty
        this[offerClick2Credit] = data.offerClick2Credit
        this[offerMoneyBoxFieldTesting] = data.offerMoneyBoxFieldTesting
        this[offerPrivateBanking] = data.offerPrivateBanking
        this[requireReviewPersonalData] = data.requireReviewPersonalData
        this[requireTermsConditionsAgreement] = data.requireTermsConditionsAgreement
        this[sendDynatraceBeacons] = data.sendDynatraceBeacons
    }

    private fun UpdateStatement.fromModel(data: FeaturesModel) {
        this[incomeExpenseWidgetVariant] = data.incomeExpenseWidgetVariant
        this[offerAroundTheProperty] = data.offerAroundTheProperty
        this[offerClick2Credit] = data.offerClick2Credit
        this[offerMoneyBoxFieldTesting] = data.offerMoneyBoxFieldTesting
        this[offerPrivateBanking] = data.offerPrivateBanking
        this[requireReviewPersonalData] = data.requireReviewPersonalData
        this[requireTermsConditionsAgreement] = data.requireTermsConditionsAgreement
        this[sendDynatraceBeacons] = data.sendDynatraceBeacons
    }

    fun updateOrInsert(devId: String, data: FeaturesModel, clientIp: String): Int {
        // Update-Versuch
        val updatedRows = update({ deviceId eq devId }) {
            it[lastCommitIp] = clientIp
            it.fromModel(data)
        }
        return if (updatedRows > 0) {
            updatedRows // Rückgabe der Anzahl der aktualisierten Zeilen
        } else {
            insert {
                it[lastCommitIp] = clientIp
                it[deviceId] = devId
                it.fromModel(data) // für InsertStatement
            }
            1 // Rückgabe von 1, um anzuzeigen, dass ein Eintrag eingefügt wurde
        }
    }
    fun toModel(row: ResultRow): FeaturesModel {
        return FeaturesModel(
            dataModelVersion = CURRENT_DATA_MODEL_VERSION,
            deviceId = row[deviceId],
            incomeExpenseWidgetVariant = row[incomeExpenseWidgetVariant],
            offerAroundTheProperty = row[offerAroundTheProperty],
            offerClick2Credit = row[offerClick2Credit],
            offerMoneyBoxFieldTesting = row[offerMoneyBoxFieldTesting],
            offerPrivateBanking = row[offerPrivateBanking],
            requireReviewPersonalData = row[requireReviewPersonalData],
            requireTermsConditionsAgreement = row[requireTermsConditionsAgreement],
            sendDynatraceBeacons = row[sendDynatraceBeacons],
        )
    }
}
