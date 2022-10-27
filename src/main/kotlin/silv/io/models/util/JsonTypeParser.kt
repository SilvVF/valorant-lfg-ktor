package silv.io.models.util

import io.ktor.websocket.*
import kotlinx.serialization.KSerializer
import silv.io.models.SocketObject


interface JsonTypeParser {

    fun parse(frame: Frame.Text): SocketObject

    fun getTypeSerializerFromString(typeAsString: String): KSerializer<out SocketObject>

    companion object {
        class JsonTypeParsingException(message: String) : Exception(message)
    }
}