package silv.io.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.websocket.serialization.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import silv.io.models.Message
import silv.io.models.Post
import silv.io.models.PostList
import silv.io.models.SocketObject
import silv.io.models.requests.Join
import silv.io.models.util.JsonTypeParser
import silv.io.models.util.JsonTypeParserImpl
import silv.io.server
import java.util.UUID


fun Application.configureSockets() {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        })
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
            webSocketWithType { session, data ->
                when (data) {
                    is Message -> {
                        println(data)
                    }
                    is Post -> {
                        println(data)
                    }
                    is Join -> {
                        server.joinRoom(
                            webSocketSession = session,
                            postId = data.id
                        )
                        println(server.rooms.getOrDefault(data.id, "empty"))
                    }
                    else -> println("Not Message")
                }
            }

        route("/posts") {
            get {
                call.respond(Json.encodeToString(PostList.serializer(), PostList(Post.sampleData())))
            }
        }
    }
}


private val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
}

val jsonTypeParser: JsonTypeParser = JsonTypeParserImpl()

fun Route.webSocketWithType(
    _execute: suspend (
        session: WebSocketSession,
        payload: Any
    ) -> Unit
) {
    webSocket("/ws/init") {
        incoming.receiveAsFlow().collect {
            try {
                if (it is Frame.Text) {
                    val data = jsonTypeParser.parse(it)
                    println(data)
                     _execute(
                            this@webSocket,
                            data
                     )
                }
            } catch (e: JsonTypeParser.Companion.JsonTypeParsingException) {
                println(e.message)
                return@collect
            } catch (e: Exception) {
                e.printStackTrace()
                return@collect
            }
        }
    }
}




