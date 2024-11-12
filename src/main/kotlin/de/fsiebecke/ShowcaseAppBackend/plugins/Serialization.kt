package de.fsiebecke.ShowcaseAppBackend.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.time.LocalDateTime

fun Application.configureSerialization() {
    // Erstelle ein SerializersModule, um benutzerdefinierte Serializer zu registrieren
    val module = SerializersModule {
        contextual(LocalDateTime::class, LocalDateTimeSerializer) // Registriere den LocalDateTimeSerializer
    }

    // Installiere ContentNegotiation mit JSON-Serialisierung und dem benutzerdefinierten Serializer-Modul
    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = module // Registriere das SerializersModule
                isLenient = true // Optional, wenn du unstrukturierte JSON akzeptieren willst
                ignoreUnknownKeys = true // Optional, ignoriert unbekannte Keys
            }
        )
    }
}

fun createJson(): Json {
    val module = SerializersModule {
        contextual(LocalDateTimeSerializer)
    }

    return Json {
        serializersModule = module
        ignoreUnknownKeys = true // Optional: wenn du unbekannte Felder ignorieren möchtest
    }
}
