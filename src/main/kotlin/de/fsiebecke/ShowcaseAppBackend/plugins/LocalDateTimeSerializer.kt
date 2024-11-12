package de.fsiebecke.ShowcaseAppBackend.plugins

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    // Liste von unterstützten Formaten
    private val formatters = listOf(
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
        DateTimeFormatter.ISO_LOCAL_DATE_TIME // Standard-ISO-Format
    )

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatters[0])) // Verwende das erste Format zum Serialisieren
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        val dateString = decoder.decodeString()
        for (formatter in formatters) {
            try {
                return LocalDateTime.parse(dateString, formatter)
            } catch (e: DateTimeParseException) {
                // Ignoriere den Fehler und versuche das nächste Format
            }
        }
        // Falls kein Format passt, eine Exception werfen
        // throw DateTimeParseException("Unparsable date: $dateString", dateString, 0)
        println("Unable to parse date '$dateString' with any known format.")
        return LocalDateTime.MIN
    }
}