fun main() {
    fun getNeighboringValues(input: List<List<Int>>, xy: Pair<Int, Int,>): List<Int> {
        return getBlock(input, xy).map { input[it.first][it.second] }
    }

    fun part1(input: List<MutableList<Int>>): Int {
        var flashesCount = 0
        for (step in 1..100) {
            step(input)
            flashesCount += input.flatten().count { it == 0 }
        }
        return flashesCount
    }

    fun isSynchronized(input: List<List<Int>>): Boolean {
        input.flatten().forEach {
            if (it != input.first().first()) return false
        }
        return true
    }

    fun part2(input: List<MutableList<Int>>): Int {
        var step = 0
        while (!isSynchronized(input)) {
            println("After step: ${step++}")
            step(input)
            input.forEach { println(it) }
        }
        return step
    }

    fun inputToIntMatrix(input: List<String>): List<MutableList<Int>> {
        return input.map { it.map{ c -> Character.getNumericValue(c) }.toMutableList() }
    }

    val testInput = listOf(
        "5483143223",
        "2745854711",
        "5264556173",
        "6141336146",
        "6357385478",
        "4167524645",
        "2176841721",
        "6882881134",
        "4846848554",
        "5283751526"
    )
    val input = readInput("Day11")
    check(part1(inputToIntMatrix(testInput)) == 1656)
    println(part1(inputToIntMatrix(input)))
    check(part2(inputToIntMatrix(testInput)) == 195)
    println(part2(inputToIntMatrix(input)))
}

// Helper functions

fun getBlock(input: List<List<Int>>, xy: Pair<Int, Int>): Set<Pair<Int, Int>> {
    val (x, y) = xy
    val coordinates = mutableSetOf<Pair<Int, Int>>()
    val xs = listOf(x - 1, x, x + 1).filter { input.indices.contains(it) }
    val ys = listOf(y - 1, y, y + 1).filter { input[0].indices.contains(it) }
    xs.forEach { xit ->
        ys.forEach { yit ->
            coordinates.add(Pair(xit, yit))
        }
    }
    return coordinates
}

fun flash(octopus: Pair<Int, Int>, input: List<MutableList<Int>>) {
    getBlock(input, octopus).forEach {
        if (input[it.first][it.second] <= 9) {
            input[it.first][it.second]++
        }
    }

}

fun step(input: List<MutableList<Int>>) {
    val octopi = (0 until 100).map {
        Pair((it / 10), it % 10)
    }
    octopi.forEach { octopus ->
        input[octopus.first][octopus.second]++
    }
    var octopus: Pair<Int, Int>
    while (true) {
        try {
            octopus = octopi.first { input[it.first][it.second] == 10 }
            flash(octopus, input)
            input[octopus.first][octopus.second]++
        } catch (e: NoSuchElementException) {
            break
        }
    }
    input.forEachIndexed { i, ints ->
        ints.forEachIndexed { j, v ->
            if (v >= 10) input[i][j] = 0
        }
    }
}
