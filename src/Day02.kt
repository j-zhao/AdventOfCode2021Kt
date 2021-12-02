/**
 * https://adventofcode.com/2021/day/2
 */

fun main() {
    fun part1(input: List<String>): Int {
        var horizontalPos = 0
        var depth = 0
        input.forEach {
            val args = it.split(" ")
            when (args[0]) {
                "forward" -> horizontalPos += args[1].toInt()
                "up" -> depth -= args[1].toInt()
                "down" -> depth += args[1].toInt()
            }
        }
        return horizontalPos * depth
    }

    fun part2(input: List<String>): Int {
        var aim = 0
        var horizontalPos = 0
        var depth = 0
        input.forEach {
            val (dir, value) = it.split(" ")
            when (dir) {
                "forward" -> {
                    depth += aim * value.toInt()
                    horizontalPos += value.toInt()
                }
                "up" -> aim -= value.toInt()
                "down" -> aim += value.toInt()
            }
        }
        return horizontalPos * depth
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
