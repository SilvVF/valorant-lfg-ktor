package silv.io.models.util

import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test
import silv.io.models.Message

class JsonTypeParserImplTest {

    private lateinit var typeParserImpl: JsonTypeParserImpl

    @Test fun `Message Type is properly parsed`() {

        typeParserImpl = JsonTypeParserImpl()

        val testMessage = Message("test",  "test")
        val actual = typeParserImpl.parse(
            Frame.Text(
                Json.encodeToString(Message.serializer(), testMessage.copy())
            )
        )

        val expected = testMessage.copy()

        assertEquals(expected, actual)
    }
}