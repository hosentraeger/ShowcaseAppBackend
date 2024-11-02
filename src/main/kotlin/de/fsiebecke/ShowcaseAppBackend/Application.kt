package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.plugins.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File



fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting()

    // Statische Dateien bereitstellen
    routing {
        staticFiles("/static", File("src/main/resources/static"))

        // Wenn der Benutzer die Hauptseite besucht
        get("/devicelist") {
            call.respondFile(File("src/main/resources/static/devicelist.html"))
        }
    }
}
