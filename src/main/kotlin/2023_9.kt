import kotlin.system.measureTimeMillis

private fun parseLine(line: String): List<List<Int>> {
    val numbersLists = mutableListOf(line.split(" ").map { it.toInt() }.toMutableList())

    while (numbersLists.last().any { it != 0}) {
        numbersLists.add(mutableListOf())
        for (i in 0 ..< numbersLists[numbersLists.size - 2].size - 1) {
            numbersLists.last().add(
                numbersLists[numbersLists.size - 2][i + 1] - numbersLists[numbersLists.size - 2][i]
            )
        }
    }

    return numbersLists
}
private fun part1() = println("Run duration: ${measureTimeMillis {
    val response = getResourceAsLinesStream("2023_9.txt").fold(0L) { acc, line ->
        acc + parseLine(line).foldRight(0) { numbers, acc2 -> 
            acc2 + numbers.last()
        }
    }
    
    println("response: $response")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    val response = getResourceAsLinesStream("2023_9.txt").fold(0L) { acc, line ->
        acc + parseLine(line).foldRight(0) { numbers, acc2 ->
            numbers.first() - acc2
        }
    }

    println("response: $response")
}} ms")

fun main() {
    println("part 1:")
    part1() // 2174807968
    println("=====")
    println("part 2:")
    part2() // 1208
}
