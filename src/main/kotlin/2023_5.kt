import kotlin.system.measureTimeMillis

data class Data(
    val seeds: List<Long>,
    val maps: List<List<List<Long>>>
)

private fun parse(): Data {
    val seeds = mutableListOf<Long>()
    val maps = mutableListOf<MutableList<List<Long>>>()

    getResourceAsLinesStream("2023_5.txt")
        .forEachIndexed { index, line ->
            if (index == 0 && line.startsWith("seeds:")) {
                seeds.addAll(line.split(": ")[1].split(" ").map { it.toLong() })
                return@forEachIndexed
            }

            when {
                line.endsWith("map:") -> maps.add(mutableListOf())
                line.isBlank() -> return@forEachIndexed
                else -> maps.last().add(line.split(" ").map { it.toLong() })
            }
        }

    return Data(seeds, maps)
}

private fun lookupMap(value: Long, map: List<List<Long>>): Long {
    val entry = map.firstOrNull {
        (it[1] ..< it[1] + it[2]).contains(value)
    } ?: return value

    return entry[0] + value - entry[1]
}

private fun reverseLookupMap(value: Long, map: List<List<Long>>): Long {
    val entry = map.firstOrNull {
        (it[0] ..< it[0] + it[2]).contains(value)
    } ?: return value

    return entry[1] + value - entry[0]
}

private fun part1() = println("Run duration: ${measureTimeMillis {
    val (seeds, maps) = parse()
    
    var response = Long.MAX_VALUE
    var location: Long
    seeds.forEach {
        location = maps.fold(it, ::lookupMap)
        if (location < response) response = location
    }
    
    println(response)
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    val (seeds, maps) = parse()
    
    val reversedMaps = maps.asReversed()
    val seedsRanges = seeds.chunked(2).map { it[0] ..< it[0] + it[1] }
    val locationsRanges = maps.last().sortedBy { it[0] }.map { it[0] ..< it[0] + it[2] }

    var response: Long = 0
    mainLoop@ for (locationRange in locationsRanges) {
        for (location in locationRange) {
            val seed = reversedMaps.fold(location, ::reverseLookupMap)

            if (seedsRanges.any { it.contains(seed) }) {
                response = location
                break@mainLoop
            }
        }
    }
    
    println("response: $response")
}} ms")


fun main() {
    println("part 1:")
    part1() // 379811651 - almost instant
    println("=====")
    println("part 2:")
    part2() // 27992443 - takes ~20 seconds to compute on my side
}
