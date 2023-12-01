import kotlin.math.pow
import kotlin.system.measureTimeMillis

private fun parseLine(line: String) = line
    .split(":  ", ": ")[1]
        .split(" |  ", " | ")
        .map { nbs -> nbs.split("  ", " ")
            .map{ it.toInt() }
            .toSet()
        }

private fun part1() = println("Run duration: ${measureTimeMillis {
    val response = getResourceAsLinesStream("2023_4.txt")
        .fold(0) { acc, line -> 
            val (winningNumbers, numbers) = parseLine(line)
            val commonNumbers = winningNumbers.intersect(numbers).size
            
            if (commonNumbers == 0) acc
            else acc + 2.0.pow(commonNumbers-1).toInt()
            
        }
    
    println("response: $response")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    val cardsCopies = mutableMapOf<Int, Int>()
    
    getResourceAsLinesStream("2023_4.txt")
        .forEachIndexed { index, line ->
            val (winningNumbers, numbers) = parseLine(line)
            val commonNumbers = winningNumbers.intersect(numbers).size
            
            for (nextCardIndex in index + 2..< index + 2 + commonNumbers) {
                cardsCopies[nextCardIndex] = (cardsCopies[nextCardIndex] ?: 0) + (cardsCopies[index + 1] ?: 0) + 1
            }
        }
    
    println("response: ${cardsCopies.values.sum() + 204}")
}} ms")

fun main() {
    println("part 1:")
    part1() // 19855
    println("=====")
    println("part 2:")
    part2() // 10378710
}
