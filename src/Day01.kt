/**
 * https://adventofcode.com/2021/day/1
 */

fun main() {
    fun part1(input: List<Int>): Int { return input.windowed(2).count { it[1] > it[0] } }

    fun part2(input: List<Int>): Int { return input.windowed(4).count { it[3] > it[0] } }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    val input = readInputToInt("Day01")
    println(part1(input))
    println(part2(input))
}
