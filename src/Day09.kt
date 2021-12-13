fun main() {
    fun getNeighboringCoordinates(input: List<List<Int>>, x: Int, y: Int): List<Pair<Int, Int>> {
        val coordinates = mutableListOf<Pair<Int, Int>>()
        val xs = listOf(x - 1, x + 1).filter { it >= 0 && it < input.size }
        val ys = listOf(y - 1, y + 1).filter { it >= 0 && it < input[0].size }
        xs.forEach { coordinates.add(Pair(it, y)) }
        ys.forEach { coordinates.add(Pair(x, it)) }
        return coordinates
    }

    fun getNeighboringValues(input: List<List<Int>>, x: Int, y: Int): List<Int> {
        return getNeighboringCoordinates(input, x, y).map { input[it.first][it.second] }
    }

    fun isLowestPoint(point: Int, neighbors: List<Int>): Boolean {
        neighbors.forEach {
            if (it <= point) return false
        }
        return true
    }

    fun part1(input: List<List<Int>>): Int {
        val lowPoints = mutableListOf<Int>()
        for (x in input.indices) {
            for (y in input[0].indices) {
                if (isLowestPoint(input[x][y], getNeighboringValues(input, x, y))) lowPoints.add(input[x][y])
            }
        }
        return lowPoints.sum() + lowPoints.size
    }

    fun traverseBasinAndGetSize(input: List<MutableList<Int>>, x: Int, y: Int): Int {
        if (input[x][y] == 0) {
            input[x][y] = 1
            val neighbors = getNeighboringCoordinates(input, x, y).filter { input[it.first][it.second] != 1 }
            return neighbors.sumOf {
                traverseBasinAndGetSize(input, it.first, it.second)
            } + 1
        }
        return 0
    }

    fun part2(input: List<List<Int>>): Int {
        val basins = input.map { it.map { v -> if (v < 9) 0 else 9 }.toMutableList() }
        var top3 = arrayOf(0, 0, 0)
        for (x in input.indices) {
            for (y in input[0].indices) {
                if (input[x][y] != 9) {
                    val basinSum = traverseBasinAndGetSize(basins, x, y)
                    top3 = top3.plusElement(basinSum).sortedArrayDescending().copyOfRange(0, 3)
                }
            }
        }
        return top3[0] * top3[1] * top3[2]
    }

    val testInput = listOf(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678",
    ).map { it.map { c -> Character.getNumericValue(c) }}
    val input = readInput("Day09")
        .map { it.map { c -> Character.getNumericValue(c) }}
    check(part1(testInput) == 15)
    println(part1(input))
    check(part2(testInput) == 1134)
    println(part2(input))
}