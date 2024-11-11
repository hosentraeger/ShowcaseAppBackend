package de.fsiebecke.ShowcaseAppBackend.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(kotlinx.serialization.json.Json { prettyPrint = true; isLenient = true })
    }
}
