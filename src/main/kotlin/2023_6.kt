import kotlin.system.measureTimeMillis

private fun part1() = println("Run duration: ${measureTimeMillis {
    val (times, distances) = getResourceAsLinesStream("2023_6.txt").toList().map {
        it.split(Regex("(?:Time|Distance):\\s*"))[1].split(Regex("\\s+")).map { it.toInt() }
    }
    
    val response = times.indices.fold(1) { response, race ->
        response * (1 ..< times[race]).fold(0) { acc, i ->
            acc + if ((times[race] - i) * i > distances[race]) 1 else 0
        }
    }
    
    println("response: $response")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    val (time, distance) = getResourceAsLinesStream("2023_6.txt").toList().map {
        it.split(":")[1].replace(" ", "").toLong()
    }
    
    val response = (1..<time).sumOf {
        if ((time - it) * it > distance) 1L else 0L
    }

    println("response: $response")

}} ms")

fun main() {
    println("part 1:")
    part1() // 4811940
    println("=====")
    println("part 2:")
    part2() // 30077773
}
