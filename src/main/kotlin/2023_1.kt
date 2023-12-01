import kotlin.system.measureTimeMillis

private fun part1() = println("Run duration: ${measureTimeMillis {
    val response = getResourceAsLinesStream("2023_1.txt")
        .fold(0) { acc, line ->
            (acc + line.first { it.isDigit() }.digitToInt() * 10
                 + line.last { it.isDigit() }.digitToInt())
        }
    
    println("response: $response")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    val literalNumbers = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    
    fun String.firstNumber(reversed: Boolean = false): Int {
        for (i in if (reversed) indices.reversed() else indices) {
            when {
                get(i).isDigit() -> return get(i).digitToInt()
                else -> substring(i, length).let { part ->
                    literalNumbers
                        .indexOfFirst { part.startsWith(it) }
                        .takeUnless { it == -1 }
                        ?.let { return it + 1 }
                }
            }
        }

        throw Error("uh")
    }
    
    val response = getResourceAsLinesStream("2023_1.txt")
        .fold(0) { acc, line ->
            acc + line.firstNumber() * 10 + line.firstNumber(reversed = true)
        }

    println("response: $response")
}} ms")

fun main() {
    println("part 1:")
    part1() // 54239
    println("=====")
    println("part 2:")
    part2() // 55343
}
