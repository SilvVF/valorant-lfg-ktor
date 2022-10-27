package silv.io.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Post(
    val title: String,
    val content: String,
    val id: String,
): SocketObject("Post") {

    companion object {

        fun sampleData(): List<Post> {
            val list = mutableListOf<Post>()
            (0..50).forEach { i ->
                list.add(
                    Post(
                        title = "Title #$i",
                        content = "Sample Post Content #$i",
                        id = UUID.randomUUID().toString()
                    )
                )
            }
            return list
        }
    }
}

@Serializable
data class PostList(
    val data: List<Post>
)