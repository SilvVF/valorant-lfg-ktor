package silv.io.models


import kotlinx.serialization.Serializable


@Serializable
abstract class SocketObject(
    val type: String
)


