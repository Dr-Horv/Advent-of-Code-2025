package se.horv.util

import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.pow

data class Position(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String = "($x, $y, $z)"
    operator fun plus(other: Position): Position = Position(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Position): Position = Position(x - other.x, y - other.y, z - other.z)

    fun manhattanDistance(other: Position): Int = abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    fun difference(other: Position): Triple<Int, Int, Int> = Triple(other.x - x, other.y - y, other.z - z)
    fun euclideanDistance(other: Position): Double = sqrt(
        (x - other.x).toDouble().pow(2) +
        (y - other.y).toDouble().pow(2) +
        (z - other.z).toDouble().pow(2)
    )
}