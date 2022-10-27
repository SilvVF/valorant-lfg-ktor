package silv.io.plugins

import io.ktor.util.collections.*
import io.ktor.websocket.*

class PostServer {

    /**
     * key : [Post.id].
     * val : websocket sessions of users in the room.
     */
    val rooms = ConcurrentMap<String, List<WebSocketSession>>()

    fun joinRoom(webSocketSession: WebSocketSession, postId: String) {
        rooms[postId] = (rooms[postId] ?: emptyList()) + webSocketSession
    }
}