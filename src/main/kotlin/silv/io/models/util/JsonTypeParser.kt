package silv.io.models.util

import io.ktor.websocket.*


interface JsonTypeParser {

    fun parse(frame: Frame.Text): Any

    companion object {
        class JsonTypeParsingException(message: String) : Exception(message)
    }
}