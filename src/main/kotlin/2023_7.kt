import kotlin.reflect.full.primaryConstructor
import kotlin.system.measureTimeMillis

enum class HandType(val strength: Int) {
    PENTA(6),
    QUADRA(5),
    FULL(4),
    TRIPLE(3),
    TWO_PAIRS(2),
    ONE_PAIR(1),
    NONE(0)
}


abstract class CamelHand(
    private val cards: List<Char>,
    val bid: Int
) : Comparable<CamelHand> {
    protected abstract val type: HandType
    protected abstract val cardsStrengths: Map<Char, Int>

    override fun compareTo(other: CamelHand): Int {
        return when {
            type.strength < other.type.strength -> -1
            type.strength > other.type.strength -> 1
            else -> {
                for (i in cards.indices) {
                    return when {
                        cards[i] == other.cards[i] -> continue
                        cardsStrengths[cards[i]]!! > other.cardsStrengths[other.cards[i]]!! -> 1
                        else -> -1
                    }
                }

                0
            }
        }
    }
}

class CamelHandP1(cards: List<Char>, bid: Int) : CamelHand(cards, bid) {
    companion object {
        private val cardsStrengthsP1 = "23456789TJQKA".withIndex().associateBy({ it.value }, { it.index })
    }

    override val type by lazy {
        when {
            cards.count { it == cards.first() } == 5 -> HandType.PENTA
            cards.any { card -> cards.count { it == card } == 4 } -> HandType.QUADRA
            cards.toSet().size == 2 -> HandType.FULL
            cards.any { card -> cards.count { it == card } == 3 } -> HandType.TRIPLE
            cards.toSet().size == 3 -> HandType.TWO_PAIRS
            cards.any { card -> cards.count { it == card } == 2 } -> HandType.ONE_PAIR
            else -> HandType.NONE
        }
    }

    override val cardsStrengths = cardsStrengthsP1
}

class CamelHandP2(cards: List<Char>, bid: Int) : CamelHand(cards, bid) {
    companion object {
        private val cardsStrengthsP2 = "J23456789TQKA".withIndex().associateBy({ it.value }, { it.index })

    }

    override val type by lazy {
        val processedCards = cards.filter { it != 'J' }.toMutableList()

        processedCards
            .groupingBy { it }
            .eachCount().maxByOrNull { it.value }
            ?.let { el ->
                processedCards.addAll(MutableList(cards.size - processedCards.size) { el.key })
            }

        when {
            processedCards.isEmpty() -> HandType.PENTA
            processedCards.count { it == processedCards.first() } == 5 -> HandType.PENTA
            processedCards.any { card -> processedCards.count { it == card } == 4 } -> HandType.QUADRA
            processedCards.toSet().size == 2 -> HandType.FULL
            processedCards.any { card -> processedCards.count { it == card } == 3 } -> HandType.TRIPLE
            processedCards.toSet().size == 3 -> HandType.TWO_PAIRS
            processedCards.any { card -> processedCards.count { it == card } == 2 } -> HandType.ONE_PAIR
            else -> HandType.NONE
        }
    }

    override val cardsStrengths = cardsStrengthsP2
}

private inline fun <reified T : CamelHand> part() = println("Run duration: ${measureTimeMillis {
    val response = getResourceAsLinesStream("2023_7.txt")
        .map { line ->
            val lineParts = line.split(" ")
            T::class.primaryConstructor!!.call(
                lineParts[0].toCharArray().toList(),
                lineParts[1].toInt()
            )
        }
        .sorted()
        .withIndex()
        .sumOf { (it.index + 1) * it.value.bid }
    
    println("response: $response")
}} ms")

fun main() {
    println("part 1:")
    part<CamelHandP1>() // 248453531
    println("=====")
    println("part 2:")
    part<CamelHandP2>()  // 248781813
}
