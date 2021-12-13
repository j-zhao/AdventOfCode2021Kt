import java.util.Stack

fun main() {
    val characterScores = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val openCloseMap = mapOf(
        '(' to ')',
        '{' to '}',
        '[' to ']',
        '<' to '>'
    )

    val autocompleteScores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    fun isIncomplete(line: String): Boolean {
        val stack = Stack<Char>()
        line.forEach {
            if (openCloseMap.keys.contains(it)) {
                stack.push(openCloseMap[it])
            } else {
                if (stack.pop() != it) {
                    return false
                }
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val stack = Stack<Char>()
        val illegalChars = mutableListOf<Char>()
        input.forEach { line ->
            line.forEach { c ->
                if (openCloseMap.keys.contains(c)) {
                    stack.push(openCloseMap[c])
                } else {
                    if(stack.pop() != c) {
                        illegalChars.add(c)
                    }
                }
            }
        }
        return illegalChars.sumOf { characterScores[it]!! }
    }

    fun getCompletionChars(line: String): Stack<Char> {
        val stack = Stack<Char>()
        line.forEach {
            if (openCloseMap.keys.contains(it)) {
                stack.push(openCloseMap[it])
            } else {
                stack.pop()
            }
        }
        return stack
    }

    fun calcCompletionScore(stack: Stack<Char>): Long {
        var score = 0L
        while(stack.isNotEmpty()) {
            score = (score * 5) + autocompleteScores[stack.pop()]!!
        }
        return score
    }

    fun part2(input: List<String>): Long {
        val incompleteLines = input.filter { isIncomplete(it) }
        val scores = incompleteLines.map {
            calcCompletionScore(getCompletionChars(it))
        }.sorted()
        scores.sorted()

        return scores[scores.size / 2]
    }

    val testInput = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]",
    )
    val input = readInput("Day10")
    check(part1(testInput) == 26397)
    println(part1(input))
    check(part2(testInput) == 288957L)
    println(part2(input))
}