package de.fsiebecke.ShowcaseAppBackend.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.*

object DatabaseFactory {
    private var database: Database? = null
    private const val databaseUrl = "jdbc:mariadb://localhost:3306/showcaseapp"
    private const val databaseUser = "showcase" // Ersetze mit deinem DB-Benutzernamen
    private const val databasePassword = "Start21Prod" // Ersetze mit deinem DB-Passwort

    fun getDatabase(): Database {
        if (database == null) {
            database = Database.connect(
                url = databaseUrl,
                driver = "org.mariadb.jdbc.Driver",
                user = databaseUser,
                password = databasePassword
            )
        }
        return database!!
    }
}

fun Application.configureDatabases() {
    DatabaseFactory.getDatabase()  // Initialisiere die Datenbankverbindung
}
