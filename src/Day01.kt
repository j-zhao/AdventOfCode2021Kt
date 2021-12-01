import java.util.LinkedList

/**
 * https://adventofcode.com/2021/day/1
 */

fun main() {
    fun part1(input: List<String>): Int {
        var incrCount = -1
        var lastDepth = -1
        input.forEach {
            if (it.toInt() > lastDepth) {
                incrCount++
            }
            lastDepth = it.toInt()
        }
        return incrCount
    }

    fun part2(input: List<String>): Int {
        val depths = LinkedList<Int>()
        var incrCount = 0
        input.forEach {
            val depth = it.toInt()
            if (depths.size == 3) {
                if (depth > depths.remove()) {
                    incrCount++
                }
            }
            depths.add(depth)
        }
        return incrCount
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
