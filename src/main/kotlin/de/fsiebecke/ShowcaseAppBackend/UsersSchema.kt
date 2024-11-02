package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.UserService.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.sql.Timestamp

@Serializer(forClass = LocalDateTime::class)
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }

    // Diese Methode kann verwendet werden, um von Timestamp nach LocalDateTime zu konvertieren.
    fun fromTimestamp(timestamp: Timestamp): LocalDateTime {
        return timestamp.toLocalDateTime()
    }

    // Diese Methode kann verwendet werden, um von LocalDateTime nach Timestamp zu konvertieren.
    fun toTimestamp(localDateTime: LocalDateTime): Timestamp {
        return Timestamp.valueOf(localDateTime)
    }
}

@Serializable
data class ExposedUser(
    val loginId: String,
    val routingNumber: String,
    @Serializable(with = LocalDateTimeSerializer::class) // Den Serializer hier verwenden
    val lastLoginDateTime: LocalDateTime,
    val deviceId: String,
    val pushId: String,
    val appVariant: String
)

class UserService(database: Database) {
    object Users : Table() {
        val id = integer("id").autoIncrement()
        val loginId = varchar("login_id", length = 50)
        val routingNumber = varchar("routing_number", length = 50)
        val lastLoginDateTime = datetime("last_login_datetime")
        val deviceId = varchar("device_id", length = 50)
        val pushId = varchar("push_id", length = 50)
        val appVariant = varchar("app_variant", length = 50)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    suspend fun create(user: ExposedUser): Int = dbQuery {
        Users.insert {
            it[loginId] = user.loginId
            it[routingNumber] = user.routingNumber
            it[lastLoginDateTime] = user.lastLoginDateTime
            it[deviceId] = user.deviceId
            it[pushId] = user.pushId
            it[appVariant] = user.appVariant
        }[Users.id]
    }

    suspend fun update(id: Int, user: ExposedUser) {
        dbQuery {
            Users.update({ Users.id eq id }) {
                it[loginId] = user.loginId
                it[routingNumber] = user.routingNumber
                it[lastLoginDateTime] = user.lastLoginDateTime
                it[deviceId] = user.deviceId
                it[pushId] = user.pushId
                it[appVariant] = user.appVariant
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            Users.deleteWhere { Users.id.eq(id) }
        }
    }

    suspend fun read(id: Int): ExposedUser? {
        return dbQuery {
            Users.selectAll().where { Users.id eq id }
                .map {
                    ExposedUser(
                        it[Users.loginId],
                        it[Users.routingNumber],
                        it[Users.lastLoginDateTime],
                        it[Users.deviceId],
                        it[Users.pushId],
                        it[Users.appVariant]
                    )
                }
                .singleOrNull()
        }
    }

    suspend fun getAllUsers(): List<ExposedUser> {
        return dbQuery {
            Users.selectAll().map {
                ExposedUser(
                    it[Users.loginId],
                    it[Users.routingNumber],
                    it[Users.lastLoginDateTime],
                    it[Users.deviceId],
                    it[Users.pushId],
                    it[Users.appVariant]
                )
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
