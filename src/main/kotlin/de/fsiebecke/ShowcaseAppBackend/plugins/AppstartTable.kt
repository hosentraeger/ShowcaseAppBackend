package de.fsiebecke.ShowcaseAppBackend.plugins

import de.fsiebecke.ShowcaseAppBackend.CURRENT_DATA_MODEL_VERSION
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import java.time.LocalDateTime

object AppstartTable : Table("showcase_appstart") {
    val deviceId = varchar("deviceId", 255).uniqueIndex() // Primary Key
    val lastLoginDateTime = datetime("last_login_date_time").nullable()
    val mergeAssistantBlz = varchar("features_requiring_attention", 255).nullable()
    val mergeAssistantFromDateTime = datetime("merge_assistant_from_date_time").nullable()
    val mergeAssistantToDateTime = datetime("merge_assistant_to_date_time").nullable()
    val minRecommendedVersion = varchar("min_recommended_version", 255).nullable()
    val minRequiredVersion = varchar("min_required_version", 255).nullable()
    val lastCommitIp = varchar("last_commit_ip", 255).nullable()

    private fun InsertStatement<*>.fromModel(data: AppstartModel) {
        this[minRecommendedVersion] = data.minRecommendedVersion?.let {
            Json.encodeToString(it)
        }

        this[minRequiredVersion] = data.minRequiredVersion?.let {
            Json.encodeToString(it)
        }

        this[mergeAssistantBlz] = data.mergeAssistantBlz?.let {
            Json.encodeToString(it)
        }
        this[mergeAssistantFromDateTime] = data.mergeAssistantFromDateTime
        this[mergeAssistantToDateTime] = data.mergeAssistantToDateTime
    }


    private fun UpdateStatement.fromModel(data: AppstartModel) {
        this[minRecommendedVersion] = data.minRecommendedVersion?.let {
            Json.encodeToString(it)
        }

        this[minRequiredVersion] = data.minRequiredVersion?.let {
            Json.encodeToString(it)
        }

        this[mergeAssistantBlz] = data.mergeAssistantBlz?.let {
            Json.encodeToString(it)
        }
        this[mergeAssistantFromDateTime] = data.mergeAssistantFromDateTime
        this[mergeAssistantToDateTime] = data.mergeAssistantToDateTime
    }

    fun updateOrInsert(devId: String, data: AppstartModel, clientIp: String): Int {
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
    fun toModel(row: ResultRow): AppstartModel {
        return AppstartModel(
            dataModelVersion = CURRENT_DATA_MODEL_VERSION,
            deviceId = row[deviceId],
            minRecommendedVersion = row[minRecommendedVersion]?.let { jsonString ->
                Json.decodeFromString<List<String>>(jsonString)
            },
            minRequiredVersion = row[minRequiredVersion]?.let { jsonString ->
                Json.decodeFromString<List<String>>(jsonString)
            }
        )
    }
}
