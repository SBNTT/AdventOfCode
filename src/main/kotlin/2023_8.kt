import kotlin.system.measureTimeMillis

private fun part1() = println("Run duration: ${measureTimeMillis {
    val lines = getResourceAsLinesStream("2023_8.txt").toList()
    
    val instructions = lines.first().toCharArray().map {
        if (it == 'L') 0 else 1
    }
    
    val map = lines.subList(2, lines.size).associate { line ->
        val parts = line.split(" = (", ", ", ")")
        Pair(parts[0], listOf(parts[1], parts[2]))
    }
    
    var acc = 0
    var state = "AAA"
    while (state != "ZZZ") {
        state = map[state]!![instructions[acc % instructions.size]]
        acc += 1
    }
    
    println("response: $acc")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    val lines = getResourceAsLinesStream("2023_8.txt").toList()

    val instructions = lines.first().toCharArray().map {
        if (it == 'L') 0 else 1
    }
    
    val map = lines.subList(2, lines.size).associate { line ->
        val parts = line.split(" = (", ", ", ")")
        Pair(parts[0], listOf(parts[1], parts[2]))
    }

    var acc = 0
    val states = map.keys.filter { it.last() == 'A' }.toMutableList()
    var state: String
    
    val steps = states.map { 
        acc = 0
        state = it

        while (state.last() != 'Z') {
            state = map[state]!![instructions[acc % instructions.size]]
            acc += 1
        }
        
        acc
    }
    
    println(steps.lcm())
}} ms")

fun main() {
    println("part 1:")
    part1() // 11567
    println("=====")
    println("part 2:")
    part2() // 9858474970153
}
