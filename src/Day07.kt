import kotlin.math.abs

fun main() {
    fun findDelta(list: List<Int>, pos: Int): Int {
        var delta = 0
        list.forEach {
            delta += abs(it - pos)
        }
        return delta
    }

    fun calcFuel(list: List<Int>, pos: Int): Int {
        var totalFuel = 0
        list.forEach {
            val diff = abs(it - pos)
            totalFuel += ((diff * (diff + 1)) / 2)
        }
        return totalFuel
    }

    fun part1(input: List<Int>): Int {
        val sortedInput = input.sorted()
        val mean = input.sum().toDouble() / input.size.toDouble()
        var position = mean.toInt()
        var min = findDelta(sortedInput, position)
        val increasing = findDelta(sortedInput, position + 1) < min
        while (true) {
            if (increasing) position++ else position--
            val delta = findDelta(sortedInput, position)
            if (delta < min) min = delta else break
        }
        return min
    }

    fun part2(input: List<Int>): Int {
        val mean = input.sum().toDouble() / input.size.toDouble()
        var min = Int.MAX_VALUE
        for (i in mean.toInt()..mean.toInt() + 1) {
            min = calcFuel(input, i).coerceAtMost(min)
        }
        return min
    }

    val input = readInput("Day07").let { it[0].split(",").map { v -> v.toInt() }}
    val testInput = listOf(16,1,2,0,4,2,7,1,2,14)
    check(part1(testInput) == 37)
    println(part1(input))
    check(part2(testInput) == 168)
    println(part2(input))
}