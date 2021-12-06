import java.util.*

fun main() {
    class SchoolOfLanternfish(fishes: List<Int>) {
        var school = (0..8).map {0L}.toMutableList()

        init { fishes.forEach { school[it] = school[it] + 1 }}

        fun countFish(): Long { return school.sum() }

        fun tick() {
            Collections.rotate(school, -1)
            school[6] = school[6] + school[8]
        }
    }

    fun part1(input: List<Int>, days: Int): Long {
        val schoolOfLanternfish = SchoolOfLanternfish(input)
        for (day in 1..days) schoolOfLanternfish.tick()
        return schoolOfLanternfish.countFish()
    }

    val input = readInput("Day06")[0].split(",").map { it.toInt() }
    val testInput = listOf("3", "4", "3", "1", "2").map{ it.toInt() }
    check(part1(testInput, 18) == 26L)
    check(part1(testInput, 80) == 5934L)
    println(part1(input, 80))
    println(part1(input, 256))
}