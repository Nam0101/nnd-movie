package com.nndmove.app.config

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.pattern.CompositeConverter
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import org.springframework.boot.ansi.AnsiColor
import org.springframework.boot.ansi.AnsiElement
import org.springframework.boot.ansi.AnsiOutput
import org.springframework.boot.ansi.AnsiStyle
import java.util.*

/**
 * Log filter to prevent attackers from forging log entries by submitting input containing CRLF characters.
 * CRLF characters are replaced with a red colored _ character.
 *
 * @see [Log Forging Description](https://owasp.org/www-community/attacks/Log_Injection)
 *
 * @see [JHipster issue](https://github.com/jhipster/generator-jhipster/issues/14949)
 */
open class CRLFLogConverter : CompositeConverter<ILoggingEvent>() {
    override fun transform(event: ILoggingEvent, `in`: String): String {
        val element = ELEMENTS[firstOption]
        val markers = event.markerList
        if ((markers != null && !markers.isEmpty() && markers[0].contains(CRLF_SAFE_MARKER)) || isLoggerSafe(event)) {
            return `in`
        }
        val replacement = if (element == null) "_" else toAnsiString("_", element)
        return `in`.replace("[\n\r\t]".toRegex(), replacement)
    }

    private fun isLoggerSafe(event: ILoggingEvent): Boolean {
        for (safeLogger in SAFE_LOGGERS) {
            if (event.loggerName.startsWith(safeLogger)) {
                return true
            }
        }
        return false
    }

    private fun toAnsiString(`in`: String?, element: AnsiElement?): String {
        return AnsiOutput.toString(element, `in`)
    }

    companion object {
        val CRLF_SAFE_MARKER: Marker = MarkerFactory.getMarker("CRLF_SAFE")

        private val SAFE_LOGGERS = arrayOf(
            "org.hibernate",
            "org.springframework.boot.autoconfigure",
            "org.springframework.boot.diagnostics",
        )
        private val ELEMENTS: Map<String, AnsiElement>

        init {
            val ansiElements: MutableMap<String, AnsiElement> = HashMap()
            ansiElements["faint"] = AnsiStyle.FAINT
            ansiElements["red"] = AnsiColor.RED
            ansiElements["green"] = AnsiColor.GREEN
            ansiElements["yellow"] = AnsiColor.YELLOW
            ansiElements["blue"] = AnsiColor.BLUE
            ansiElements["magenta"] = AnsiColor.MAGENTA
            ansiElements["cyan"] = AnsiColor.CYAN
            ELEMENTS = Collections.unmodifiableMap(ansiElements)
        }
    }
}
