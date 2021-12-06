/*
https://adventofcode.com/2021/day/5
 */

fun main() {
    data class Coordinate(
        val x: Int,
        val y: Int,
    )

    fun traverseCoordinates(input: String, map: MutableMap<Coordinate, Int>, includeDiagonals: Boolean) {
        val matches = Regex("\\d+").findAll(input)
        val numbers = matches.map { it.value.toInt() }.toList()
        val coordinates = numbers.windowed(2, 2).map { Coordinate(it[0], it[1]) }.sortedBy { it.x }
        val traversals = mutableListOf<Coordinate>()
        if (coordinates[0].x == coordinates[1].x) {
            if (coordinates[0].y < coordinates[1].y) {
                for (y in coordinates[0].y..coordinates[1].y) {
                    traversals.add(Coordinate(coordinates[0].x, y))
                }
            } else {
                for (y in coordinates[1].y..coordinates[0].y) {
                    traversals.add(Coordinate(coordinates[0].x, y))
                }
            }
        } else if (coordinates[0].y == coordinates[1].y) {
            if (coordinates[0].x < coordinates[1].x) {
                for (x in coordinates[0].x..coordinates[1].x) {
                    traversals.add(Coordinate(x, coordinates[0].y))
                }
            } else {
                for (x in coordinates[1].x..coordinates[0].x) {
                    traversals.add(Coordinate(x, coordinates[0].y))
                }
            }
        } else if (includeDiagonals) {
            if (coordinates[0].y < coordinates[1].y) {
                var x = coordinates[0].x
                for (y in coordinates[0].y..coordinates[1].y) {
                    traversals.add(Coordinate(x, y))
                    x++
                }
            } else {
                var x = coordinates[0].x
                for (y in coordinates[0].y downTo coordinates[1].y) {
                    traversals.add(Coordinate(x, y))
                    x++
                }
            }
        }
        traversals.forEach {
            map[it] = map.getOrDefault(it, 0) + 1
        }
    }

    fun part1(input: List<String>): Int {
        val coordinatesMap = mutableMapOf<Coordinate, Int>()
        input.forEach { traverseCoordinates(it, coordinatesMap, false) }
        val count = coordinatesMap.count { it.value >= 2 }
        return count
    }

    fun part2(input: List<String>): Int {
        val coordinatesMap = mutableMapOf<Coordinate, Int>()
        input.forEach { traverseCoordinates(it, coordinatesMap, true) }
        return coordinatesMap.count { it.value >= 2 }
    }

    val input = readInput("Day05")
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    println(part1(input))
    check(part2(testInput) == 12)
    println(part2(input))
}
