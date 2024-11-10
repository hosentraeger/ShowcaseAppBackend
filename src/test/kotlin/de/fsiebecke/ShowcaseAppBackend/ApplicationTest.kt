package de.fsiebecke.ShowcaseAppBackend

import de.fsiebecke.ShowcaseAppBackend.plugins.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {

        application {
            configureDatabases()
            configureRouting()
        }
    }
}
