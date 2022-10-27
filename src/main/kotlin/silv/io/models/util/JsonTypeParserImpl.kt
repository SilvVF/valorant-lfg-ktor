package silv.io.models.util

import io.ktor.serialization.*
import io.ktor.server.request.*
import io.ktor.websocket.*
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.internal.throwArrayMissingFieldException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer
import silv.io.models.Message
import silv.io.models.Post
import silv.io.models.SocketObject
import silv.io.models.requests.Join
import java.util.StringJoiner

class JsonTypeParserImpl: JsonTypeParser {

    @OptIn(InternalSerializationApi::class)
    override fun parse(frame: Frame.Text): Any {

        val stringFrame = frame.readText()

        val typeAsString = Json.parseToJsonElement(stringFrame).jsonObject["type"]
            ?.toString()
            ?.removePrefix("\"")?.removeSuffix("\"")
            ?: throw JsonTypeParser.Companion.JsonTypeParsingException("could not find type field in Frame $stringFrame")

        val typeSerializer = when (typeAsString) {
            "Message" -> Message::class.serializer()
            "SocketObject" -> SocketObject::class.serializer()
            "Post" -> Post::class.serializer()
            "Join" -> Join::class.serializer()
            else -> throw JsonTypeParser.Companion.JsonTypeParsingException(
                "Could not find a serializer for the type $typeAsString."
            )
        }
        println(stringFrame)
        println(typeAsString)
        println(typeSerializer)
        return Json.decodeFromString(typeSerializer, stringFrame)
    }
}