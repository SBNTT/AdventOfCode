import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

private data class Galaxy(
    val row: Int,
    val col: Int,
)

private val map by lazy {
    getResourceAsLinesStream("2023_11.txt").toList()
}

private val galaxies by lazy {
    val galaxies = mutableListOf<Galaxy>()
    for (r in map.indices) {
        for (c in map[r].indices) {
            if (map[r][c] == '#') {
                galaxies.add(Galaxy(r, c))
            }
        }
    }
    
    galaxies
}

private fun calculateDistances(expansionRatio: Int): Long {
    var acc = 0L
    var voidSteps: Int
    
    for (i in 0 ..< galaxies.size) {
        for (j in i + 1 ..< galaxies.size) {
            acc += abs(galaxies[i].row - galaxies[j].row)
            acc += abs(galaxies[i].col - galaxies[j].col)

            
            voidSteps = (min(galaxies[i].row, galaxies[j].row) + 1 ..< max(galaxies[i].row, galaxies[j].row)).count { r -> 
                !map[r].any { it == '#' }
            }
            
            voidSteps += (min(galaxies[i].col, galaxies[j].col) + 1 ..< max(galaxies[i].col, galaxies[j].col)).count { c -> 
                !map.any { it[c] == '#'  }
            }
            
            acc += voidSteps * expansionRatio

            if (expansionRatio > 1) {
                acc -= voidSteps
            }
        }
    }
    
    return acc
}

private fun part1() = println("Run duration: ${measureTimeMillis {
    println("response: ${calculateDistances(1)}")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    println("response: ${calculateDistances(1_000_000)}")
}} ms")

fun main() {
    println("part 1:")
    part1() // 9684228
    println("=====")
    println("part 2:")
    part2() // 483844716556
}
