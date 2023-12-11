import kotlin.streams.asSequence

fun getResourceAsLinesStream(name: String) =
    (object {}.javaClass.getResourceAsStream(name) ?: throw Error("Resource not found"))
        .bufferedReader()
        .lines()
        .asSequence()


fun lcm(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}

fun List<Int>.lcm(): Long {
    var result = get(0).toLong()
    for (i in 1..<size) {
        result = lcm(result, get(i).toLong())
    }
    return result
}
