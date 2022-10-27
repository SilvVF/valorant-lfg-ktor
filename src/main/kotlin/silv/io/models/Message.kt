package silv.io.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    val text: String,
): SocketObject("Message")


