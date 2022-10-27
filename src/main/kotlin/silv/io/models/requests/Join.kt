package silv.io.models.requests

import kotlinx.serialization.Serializable
import silv.io.models.SocketObject

@Serializable
data class Join(
    val id: String,
    val text: String
): SocketObject("Join")