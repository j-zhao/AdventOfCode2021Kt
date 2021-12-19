fun main() {
    fun part1(input: List<List<Int>>): Int? {
        val endpoint = Pair(input.size - 1, input[0].size - 1)
        val start = Pair(0, 0)
        val traversed = mutableSetOf<Set<Pair<Int, Int>>>()
        var min = Int.MAX_VALUE

        fun sumRisk(visited: Set<Pair<Int, Int>>): Int {
            return visited.sumOf { input[it.first][it.second] } - input[0][0]
        }

        fun findMinRisk(xy: Pair<Int, Int>, visited: Set<Pair<Int, Int>>): Int? {
            if (xy == endpoint) {
                val currRisk = sumRisk(visited) + input[xy.first][xy.second]
                if (currRisk < min) {
                    min = currRisk
                    println(min)
                }
                return currRisk
            }
            val traversal = visited.plus(xy)
            traversed.add(traversal)
            val adjCoordinates = getAdjacentCoordinates(xy, input.size, input[0].size)
                .filter { it !in visited }
                .filter { !traversed.contains(traversal.plus(it))}
            if (adjCoordinates.isEmpty()) return null
            val risks = adjCoordinates
                .mapNotNull { findMinRisk(it, traversal) }
            if (risks.isEmpty()) return null
            return risks.minOrNull()
        }
        val startMs = System.currentTimeMillis()
        while (true) {
            findMinRisk(start, setOf(start))?.let {
                min = min.coerceAtMost(it)
            }
            println("Elapsed: ${System.currentTimeMillis() - startMs}")
            if (System.currentTimeMillis() - startMs > 30 * 1000) break
        }

        return min
    }

    val testInput = listOf(
        "1163751742",
        "1381373672",
        "2136511328",
        "3694931569",
        "7463417111",
        "1319128137",
        "1359912421",
        "3125421639",
        "1293138521",
        "2311944581",
    ).map { line -> line.map { Character.getNumericValue(it) }}
    println(part1(testInput))
}