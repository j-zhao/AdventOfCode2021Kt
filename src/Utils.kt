import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads lines from the given input txt file and maps to Ints
 */
fun readInputToInt(name: String) = readInput(name).map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun getAdjacentCoordinates(coordinate: Pair<Int, Int>, xMax: Int, yMax: Int): Set<Pair<Int, Int>> {
    return listOf(
        Pair(coordinate.first - 1, coordinate.second),
        Pair(coordinate.first + 1, coordinate.second),
        Pair(coordinate.first, coordinate.second - 1),
        Pair(coordinate.first, coordinate.second + 1)
    ).filter {
            it.first in (0 until xMax) && it.second in (0 until yMax)
    }.toSet()
}