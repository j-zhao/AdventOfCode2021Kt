fun main() {
    class SchoolOfLanternfish(fishes: List<Int>) {
        var school = initSchool()

        init {
            fishes.forEach {
                school[it] = school[it]!! + 1
            }
        }

        fun initSchool(): MutableMap<Int, Long> {
            return mutableMapOf<Int, Long>().apply {
                for (i in 0..8) this[i] = 0
            }
        }

        fun countFish(): Long {
            return school.values.sum()
        }

        fun tick() {
            val schoolClone = initSchool()
            school.forEach { (k, v) ->
                if (k != 0) schoolClone[k - 1] = schoolClone[k - 1]!! + v
            }
            schoolClone[6] = schoolClone[6]!! + school[0]!!
            schoolClone[8] = school[0]!!
            school = schoolClone
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