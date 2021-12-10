fun main() {
    /*
     aaaa
    b    c
    b    c
     dddd
    e    f
    e    f
     gggg
     */

    fun part1(textInput: List<String>): Int {
        var count = 0
        textInput.forEach { line ->
            count += line.split("|")[1].split(" ")
                .filterNot { it.isEmpty() }
                .count { setOf(2, 3, 4, 7).contains(it.length) }
        }
        return count
    }

    fun inferDigitsMap(inputLengthMap: Map<Int, Set<Set<Char>>>): Map<Set<Char>, Int> {
        fun Set<Char>.charsNotIn(s: Set<Char>): Set<Char> { return this.filterNot { s.contains(it) }.toSet() }
        val segmentsMap = mutableMapOf<Char, Char>()
        // map segments based on count in total digits
        val flattenedSegments = inputLengthMap.values.flatten().flatten()
        val segmentsCount = ('a'..'g').associateBy { a -> flattenedSegments.count { it == a} }
        segmentsMap['a'] = inputLengthMap[3]!!.single().charsNotIn(inputLengthMap[2]!!.single()).single()
        segmentsMap['b'] = segmentsCount[6]!!
        segmentsMap['e'] = segmentsCount[4]!!
        segmentsMap['f'] = segmentsCount[9]!!

        val digitsMap = Array<Set<Char>>(10) { emptySet() }
        inputLengthMap.forEach { (index, chars) ->
            when(index) {
                2 -> digitsMap[1] = chars.single()
                3 -> digitsMap[7] = chars.single()
                4 -> digitsMap[4] = chars.single()
                7 -> digitsMap[8] = chars.single()
            }
        }
        digitsMap[9] = digitsMap[8].minus(segmentsMap['e']!!)
        segmentsMap['g'] = digitsMap[9].charsNotIn(digitsMap[4].plus(segmentsMap['a']!!)).single()
        segmentsMap['c'] = digitsMap[7].minus(segmentsMap['a']).minus(segmentsMap['f']).single()!!
        segmentsMap['d'] = digitsMap[4].minus(segmentsMap['b']).minus(segmentsMap['c']).minus(segmentsMap['f']).single()!!
        digitsMap[0] = digitsMap[8].minus(segmentsMap['d']!!)
        digitsMap[2] = digitsMap[8].minus(segmentsMap['b']!!).minus(segmentsMap['f']!!)
        digitsMap[3] = digitsMap[2].plus(segmentsMap['f']!!).minus(segmentsMap['e']!!)
        digitsMap[5] = digitsMap[8].minus(segmentsMap['c']!!).minus(segmentsMap['e']!!)
        digitsMap[6] = digitsMap[8].minus(segmentsMap['c']!!)

        return digitsMap.indices.associateBy { digitsMap[it] }
    }

    fun part2(textInput: List<String>): Int {
        var sum = 0
        textInput.forEach { line ->
            val (inputs, outputs) = line.split("|").map { numbers ->
                numbers.split(" ").filterNot { it.isEmpty() }
            }
            val inputLengthMap = (2..7).associateWith { mutableSetOf<Set<Char>>() }
            inputs.forEach { input ->
                inputLengthMap[input.length]!!.add(input.toSet())
            }
            val digitsMap = inferDigitsMap(inputLengthMap)
            var digits = ""
            outputs.forEach {
                digits += digitsMap[it.toSet()]!!
            }
            sum += digits.toInt()
        }
        return sum
    }


    val testInput = readInput("Day08_test")
    val input = readInput("Day08")
    check(part1(testInput) == 26)
    println(part1(input))
    println(part2(input))
}


