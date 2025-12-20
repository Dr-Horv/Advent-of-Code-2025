package se.horv.day04

import se.horv.util.Coordinate
import se.horv.util.Solver

enum class Tile {
    EMPTY,
    ROLL_OF_PAPER;

    override fun toString(): String = when (this) {
        EMPTY -> "."
        ROLL_OF_PAPER -> "@"
    }
}

class Day04:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {

        val map: MutableMap<Coordinate, Tile> = mutableMapOf()
        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                map[Coordinate(x, y)] = if(c == '@') Tile.ROLL_OF_PAPER else Tile.EMPTY
            }
        }

        val paddedMap = map.withDefault { Tile.EMPTY }
        val maxY = lines.lastIndex
        val maxX = lines.first().lastIndex

        if(!partTwo) {
            var sum = 0
            for (y in 0..maxY) {
                for (x in 0..maxX) {
                    val c = Coordinate(x, y)
                    if(paddedMap.getValue(c) == Tile.ROLL_OF_PAPER && isAccessible(c, paddedMap)) {
                        sum++
                    }
                }
            }

            return sum.toString()
        } else {
            var sum = 0
            var test = true
            while (test) {
                test = false
                val currMap = map.withDefault { Tile.EMPTY }
                for (y in 0..maxY) {
                    for (x in 0..maxX) {
                        val c = Coordinate(x, y)
                        if(currMap.getValue(c) == Tile.ROLL_OF_PAPER && isAccessible(c, currMap)) {
                            sum++
                            map[c] = Tile.EMPTY
                            test = true
                        }
                    }
                }
            }


            return sum.toString()
        }

    }

    private fun isAccessible(
        c: Coordinate,
        map: Map<Coordinate, Tile>
    ): Boolean = c.adjacent().map { map.getValue(it) }.filter { it == Tile.ROLL_OF_PAPER }.size < 4

}