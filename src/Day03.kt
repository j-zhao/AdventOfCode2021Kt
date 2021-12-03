import kotlin.math.pow

/*
 * https://adventofcode.com/2021/day/3
 */

fun main() {
    fun part1(input: List<String>): Int {
        var gamma = ""
        var epsilon = ""

        generateBitArr(input).forEach {
            if (it > 0) {
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += "1"
            }
        }
        return convertBinaryStringToDecimalInt(gamma) * convertBinaryStringToDecimalInt(epsilon)
    }

    fun part2(input: List<String>): Int {
        val bits = generateBitArr(input).map {
            if (it >= 0) 1 else 0
        }

        val oxygenGeneratorRating = convertBinaryStringToDecimalInt(filterInputByBit(input, false))
        val c02ScrubberRating = convertBinaryStringToDecimalInt(filterInputByBit(input, true))
        return oxygenGeneratorRating * c02ScrubberRating
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun generateBitArr(input: List<String>): Array<Int> {
    val digits = Array(input[0].length) { 0 }
    input.forEach {
        it.forEachIndexed { index, c ->
            if (c == '1') digits[index]++
            else digits[index]--
        }
    }
    return digits
}

fun convertBinaryStringToDecimalInt(binStr: String): Int {
    var power = 0.0
    var sum = 0.0
    binStr.reversed().forEach {
        val v = if (it == '1') 1 else 0
        sum += v * 2.0.pow(power)
        power++
    }
    return sum.toInt()
}

fun filterInputByBit(input: List<String>, isInverted: Boolean): String {
    var filteredInput = input
    for (index in 0 until input[0].length) {
        var bitSum = 0
        filteredInput.forEach {
            if (it[index] == '1') bitSum++ else bitSum--
        }
        bitSum = if (!isInverted) {
            if (bitSum >= 0) 1 else 0
        } else {
            if (bitSum >= 0) 0 else 1
        }
        filteredInput = filteredInput.filter {
            it[index].toString() == bitSum.toString()
        }
        if (filteredInput.size == 1) break
    }
    return filteredInput[0]
}
