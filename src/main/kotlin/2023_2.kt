import kotlin.system.measureTimeMillis

private fun part1() = println("Run duration: ${measureTimeMillis {
    val thresholds = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14
    )

    var acc = 0

    getResourceAsLinesStream("2023_2.txt")
        .forEachIndexed { lineIndex, line ->
            line.split(": ")[1].split("; ")
                .any { subGame -> // that is impossible
                    subGame.split(", ").any {
                        val (count, color) = it.split(" ")
                        count.toInt() > thresholds[color]!!
                    }
                }
                .takeUnless { it }
                ?.let { acc += lineIndex + 1 }
        }

    println("response: $acc")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    var acc = 0
    val colorsCounts = mutableMapOf<String, Int>()

    getResourceAsLinesStream("2023_2.txt")
        .forEach { line ->
            colorsCounts.clear()
            line.split(": ")[1].split(", ", "; ").forEach {
                val (countStr, color) = it.split(" ")
                val count = countStr.toInt()

                if ((colorsCounts[color] ?: 0) < count) {
                    colorsCounts[color] = count
                }
            }
            acc += colorsCounts.values.reduce { acc, it -> acc * it  }
        }

    println("response: $acc")
}} ms")

fun main() {
    println("part 1:")
    part1() // 2716
    println("=====")
    println("part 2:")
    part2() // 72227
}
