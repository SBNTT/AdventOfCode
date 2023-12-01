import kotlin.streams.asSequence

fun getResourceAsLinesStream(name: String) =
    (object {}.javaClass.getResourceAsStream(name) ?: throw Error("Resource not found"))
        .bufferedReader()
        .lines()
        .asSequence()
