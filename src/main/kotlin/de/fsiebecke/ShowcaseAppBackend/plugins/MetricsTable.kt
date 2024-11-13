package de.fsiebecke.ShowcaseAppBackend.plugins

import de.fsiebecke.ShowcaseAppBackend.CURRENT_DATA_MODEL_VERSION
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement

// Tabelle für Metrics
object MetricsTable : Table("showcase_metrics") {
    val deviceId = varchar("deviceId", 255).uniqueIndex() // Primary Key
    val fullAppStartsLastMonth = integer("full_app_starts_last_month").nullable()
    val fullAppStartsMsLastMonth = integer("full_app_starts_ms_last_month").nullable()
    val fullAppStartsMsThisMonth = integer("full_app_starts_ms_this_month").nullable()
    val fullAppStartsMsTotal = integer("full_app_starts_ms_total").nullable()
    val fullAppStartsThisMonth = integer("full_app_starts_this_month").nullable()
    val fullAppStartsTotal = integer("full_app_starts_total").nullable()
    val subsequentAppStartsLastMonth = integer("subsequent_app_starts_last_month").nullable()
    val subsequentAppStartsMsLastMonth = integer("subsequent_app_starts_ms_last_month").nullable()
    val subsequentAppStartsMsThisMonth = integer("subsequent_app_starts_ms_this_month").nullable()
    val subsequentAppStartsMsTotal = integer("subsequent_app_starts_ms_total").nullable()
    val subsequentAppStartsThisMonth = integer("subsequent_app_starts_this_month").nullable()
    val subsequentAppStartsTotal = integer("subsequent_app_starts_total").nullable()
    val lastCommitIp = varchar("last_commit_ip", 255).nullable()

    private fun InsertStatement<*>.fromModel(data: MetricsModel) {
        this[fullAppStartsThisMonth] = data.fullAppStartsSinceLastCommit
        this[fullAppStartsMsThisMonth] = data.fullAppStartsMsSinceLastCommit
        this[fullAppStartsLastMonth] = data.fullAppStartsSinceLastCommit
        this[fullAppStartsMsLastMonth] = data.fullAppStartsMsSinceLastCommit
        this[fullAppStartsTotal] = data.fullAppStartsSinceLastCommit
        this[fullAppStartsMsTotal] = data.fullAppStartsMsSinceLastCommit
        this[subsequentAppStartsThisMonth] = data.subsequentAppStartsSinceLastCommit
        this[subsequentAppStartsMsThisMonth] = data.subsequentAppStartsMsSinceLastCommit
        this[subsequentAppStartsLastMonth] = data.subsequentAppStartsSinceLastCommit
        this[subsequentAppStartsMsLastMonth] = data.subsequentAppStartsMsSinceLastCommit
        this[subsequentAppStartsTotal] = data.subsequentAppStartsSinceLastCommit
        this[subsequentAppStartsMsTotal] = data.subsequentAppStartsMsSinceLastCommit
    }

    private fun UpdateStatement.fromModel(data: MetricsModel) {
        this[fullAppStartsThisMonth] = data.fullAppStartsSinceLastCommit
        this[fullAppStartsMsThisMonth] = data.fullAppStartsMsSinceLastCommit
        this[fullAppStartsLastMonth] = data.fullAppStartsSinceLastCommit
        this[fullAppStartsMsLastMonth] = data.fullAppStartsMsSinceLastCommit
        this[fullAppStartsTotal] = data.fullAppStartsSinceLastCommit
        this[fullAppStartsMsTotal] = data.fullAppStartsMsSinceLastCommit
        this[subsequentAppStartsThisMonth] = data.subsequentAppStartsSinceLastCommit
        this[subsequentAppStartsMsThisMonth] = data.subsequentAppStartsMsSinceLastCommit
        this[subsequentAppStartsLastMonth] = data.subsequentAppStartsSinceLastCommit
        this[subsequentAppStartsMsLastMonth] = data.subsequentAppStartsMsSinceLastCommit
        this[subsequentAppStartsTotal] = data.subsequentAppStartsSinceLastCommit
        this[subsequentAppStartsMsTotal] = data.subsequentAppStartsMsSinceLastCommit
    }

    fun updateOrInsert(devId: String, data: MetricsModel, clientIp: String): Int {
        val row = selectAll().where { deviceId eq devId }.singleOrNull()
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
        val updatedRows = update({ deviceId eq devId }) {
            it[MetricsTable.deviceId] = devId
            it[fullAppStartsThisMonth] = newFullAppStartsThisMonth
            it[subsequentAppStartsThisMonth] = newSubsequentAppStartsThisMonth
            it[fullAppStartsMsThisMonth] = newFullAppStartsMsThisMonth
            it[subsequentAppStartsMsThisMonth] = newSubsequentAppStartsMsThisMonth
            it[fullAppStartsTotal] = newFullAppStartsTotal
            it[subsequentAppStartsTotal] = newSubsequentAppStartsTotal
            it[fullAppStartsMsTotal] = newFullAppStartsMsTotal
            it[subsequentAppStartsMsTotal] = newSubsequentAppStartsMsTotal
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
    fun toModel(row: ResultRow): MetricsModel {
        return MetricsModel(
            dataModelVersion = CURRENT_DATA_MODEL_VERSION,
            deviceId = row[deviceId],
            fullAppStartsMsSinceLastCommit = 0,
            fullAppStartsSinceLastCommit = 0,
            subsequentAppStartsMsSinceLastCommit = 0,
            subsequentAppStartsSinceLastCommit = 0
        )
    }
}
