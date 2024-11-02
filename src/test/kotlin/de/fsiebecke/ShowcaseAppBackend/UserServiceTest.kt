package de.fsiebecke.ShowcaseAppBackend

import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import java.time.LocalDateTime

class UserServiceTest {

    private lateinit var database: Database
    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        // Erstelle eine In-Memory-Datenbank für die Tests
        database = Database.connect(
            "jdbc:mariadb://localhost:3306/test_database",
            driver = "org.mariadb.jdbc.Driver",
            user = "tester",
            password = "Start21Test")
        userService = UserService(database)

        // Erstelle die Tabellen
        transaction(database) {
            SchemaUtils.create(UserService.Users)
        }
    }

    @AfterEach
    fun tearDown() {
        // Lösche die Tabellen nach jedem Test
        transaction(database) {
            SchemaUtils.drop(UserService.Users)
        }
    }

    @Test
    fun `create user successfully`() = runBlocking {
        // Beispiel-Benutzerdaten
        val user = ExposedUser(
            loginId = "user123",
            routingNumber = "987654321",
            lastLoginDateTime = LocalDateTime.now(),
            deviceId = "device123",
            pushId = "push123",
            appVariant = "variantA"
        )

        // Benutzer erstellen und die ID erhalten
        val userId = userService.create(user)

        // Überprüfe, ob der Benutzer korrekt eingefügt wurde
        val retrievedUser = userService.read(userId)
        assertEquals(user.loginId, retrievedUser?.loginId)
        assertEquals(user.routingNumber, retrievedUser?.routingNumber)
        assertEquals(user.lastLoginDateTime, retrievedUser?.lastLoginDateTime)
        assertEquals(user.deviceId, retrievedUser?.deviceId)
        assertEquals(user.pushId, retrievedUser?.pushId)
        assertEquals(user.appVariant, retrievedUser?.appVariant)
    }
}
