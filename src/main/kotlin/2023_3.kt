import kotlin.system.measureTimeMillis

private fun part1() = println("Run duration: ${measureTimeMillis {
    fun Char.isSymbol() = this != '.' && !this.isDigit()
    
    var acc = 0
    val digitsBuffer = StringBuilder()
    
    getResourceAsLinesStream("2023_3.txt")
        .toList()
        .let { lines ->
            lines.forEachIndexed { lineIndex, line -> 
                line.forEachIndexed { index, char ->
                    if (char.isDigit()) digitsBuffer.append(char)
                    
                    if ((index == line.length - 1 || !char.isDigit()) && digitsBuffer.isNotEmpty()) {
                        var s = -digitsBuffer.length
                        
                        if (index != line.length - 1) {
                            s--
                        }
                        
                        if (!char.isDigit() && index == line.length - 1) {
                            s--
                        }
                        
                        
                        neighborsLoop@
                        for (r in -1..1) {
                            for (c in s..s + digitsBuffer.length + 1) {
                                if (lines.getOrNull(lineIndex + r)?.getOrNull(index + c)?.isSymbol() == true) {
                                    acc += digitsBuffer.toString().toInt()
                                    break@neighborsLoop
                                }
                            }
                        }
                        
                        digitsBuffer.clear()
                    }
                } 
            }
        }
    println("response: $acc")
}} ms")

private fun part2() = println("Run duration: ${measureTimeMillis {
    val digitsBuffer = StringBuilder()
    
    val gearsMap = mutableMapOf<String,MutableList<Int>>()

    getResourceAsLinesStream("2023_3.txt")
        .toList()
        .let { lines ->
            lines.forEachIndexed { lineIndex, line ->
                line.forEachIndexed { index, char ->
                    if (char.isDigit()) digitsBuffer.append(char)

                    if ((index == line.length - 1 || !char.isDigit()) && digitsBuffer.isNotEmpty()) {
                        var s = -digitsBuffer.length

                        if (index != line.length - 1) {
                            s--
                        }

                        if (!char.isDigit() && index == line.length - 1) {
                            s--
                        }

                        neighborsLoop@
                        for (r in -1..1) {
                            for (c in s..s + digitsBuffer.length + 1) {
                                if (lines.getOrNull(lineIndex + r)?.getOrNull(index + c) == '*') {
                                    val key = "${lineIndex + r}-${index + c}"
                                    if (!gearsMap.containsKey(key)) {
                                        gearsMap[key] = mutableListOf()
                                    }
                                    gearsMap[key]!!.add(digitsBuffer.toString().toInt())
                                    break@neighborsLoop
                                }
                            }
                        }

                        digitsBuffer.clear()
                    }
                }
            }
        }
    
    val acc = gearsMap.values
        .filter { it.size == 2 }
        .sumOf { it.reduce { acc, i -> acc * i } }

    println("response: $acc")
}} ms")

fun main() {
    println("part 1:") // 546563
    part1()
    println("=====")
    println("part 2:") // 91031374
    part2()
}
