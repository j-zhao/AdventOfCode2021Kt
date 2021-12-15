fun main() {
    val input = readInput("Day12")
    val testInput = listOf(
        "start-A",
        "start-b",
        "A-c",
        "A-b",
        "b-d",
        "A-end",
        "b-end",
    )
    val testInput2 = listOf(
        "dc-end",
        "HN-start",
        "start-kj",
        "dc-start",
        "dc-HN",
        "LN-dc",
        "HN-end",
        "kj-sa",
        "kj-HN",
        "kj-dc",
    )

    fun String.isSmallCave(): Boolean {
        if (this.isEmpty()) throw Exception("Empty string")
        this.forEach {
            if (it !in 'a'..'z') return false
        }
        return true
    }

    fun constructCavesMap(input: List<String>): Map<String, Set<String>> {
        val caves = mutableMapOf<String, MutableSet<String>>()
        input.forEach { line ->
            val (name, edge) = line.split("-")
            if (!caves.containsKey(edge)) caves[edge] = mutableSetOf()
            caves[name] = caves.getOrDefault(name, mutableSetOf()).apply { if (edge != "start") add(edge) }
            caves[edge] = caves.getOrDefault(edge, mutableSetOf()).apply { if (name != "start") add(name) }
        }
        caves.keys.forEach {
            caves[it] = caves[it]!!.toSortedSet()
        }
       return caves
    }


    fun part1(input: List<String>): Int {
        val caves = constructCavesMap(input)
        val paths = mutableSetOf<List<String>>()
        caves["start"]!!.forEach {
            fun traverse(currentNode: String, prevPath: List<String>, visitedSmallCaves: Set<String>) {
                if (currentNode == "end") {
                    paths.add(prevPath)
                } else {
                    if (!currentNode.isSmallCave()) {
                        caves[currentNode]!!.forEach { connectedCave ->
                            traverse(connectedCave, prevPath.plus(currentNode), visitedSmallCaves)
                        }
                    } else if (!visitedSmallCaves.contains(currentNode)) {
                        caves[currentNode]!!.forEach { connectedCave ->
                            traverse(
                                connectedCave,
                                prevPath.plus(currentNode),
                                visitedSmallCaves.plus(currentNode)
                            )
                        }
                    }
                }
            }

            traverse(it, listOf(), setOf())
        }
        return paths.size
    }

    fun part2(input: List<String>): Int {
        val caves = constructCavesMap(input)

        val paths = mutableSetOf<List<String>>()
        caves["start"]!!.forEach {
            fun traverse(
                currentNode: String,
                prevPath: List<String>,
                visitedSmallCaves: List<String>,
                revisitedSmallCave: String?
            ) {
                if (currentNode == "end") {
                    paths.add(prevPath)
                } else {
                    if (!currentNode.isSmallCave()) {
                        caves[currentNode]!!.forEach { connectedCave ->
                            traverse(connectedCave, prevPath.plus(currentNode), visitedSmallCaves, revisitedSmallCave)
                        }
                    } else {
                        if (visitedSmallCaves.contains(currentNode)) {
                            if (revisitedSmallCave == null) {
                                caves[currentNode]!!.forEach { connectedCave ->
                                    traverse(
                                        connectedCave,
                                        prevPath.plus(currentNode),
                                        visitedSmallCaves.plus(currentNode),
                                        currentNode
                                    )
                                }
                            }
                        } else {
                            caves[currentNode]!!.forEach { connectedCave ->
                                traverse(
                                    connectedCave,
                                    prevPath.plus(currentNode),
                                    visitedSmallCaves.plus(currentNode),
                                    revisitedSmallCave
                                )
                            }
                        }
                    }
                }
            }

            traverse(it, listOf(), listOf(), null)
        }
        return paths.size
    }

    check(part1(testInput) == 10)
    check(part1(testInput2) == 19)
    println(part1(input))
    check(part2(testInput) == 36)
    check(part2(testInput2) == 103)
    println(part2(input))
}