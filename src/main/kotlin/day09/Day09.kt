package se.horv.day09

import day09.DrawRect
import se.horv.util.Coordinate
import se.horv.util.Direction
import se.horv.util.Solver
import kotlin.collections.map
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CoordinateCompressor(val coordinates: List<Coordinate>) {
    val compressedX = coordinates.map { it.x }
        .toSet().sorted().mapIndexed { i,x -> Pair(x, i) }.toMap()
    private val decompressedX = compressedX.entries.associateBy({ it.value }) { it.key }

    val compressedY = coordinates.map { it.y }
        .toSet().sorted().mapIndexed { i,y -> Pair(y, i) }.toMap()
    private val decompressedY = compressedY.entries.associateBy({ it.value }) { it.key }

    fun compress(c: Coordinate): Coordinate = Coordinate(
        compressedX.getValue(c.x),
        compressedY.getValue(c.y)
    )
    fun decompress(c: Coordinate): Coordinate = Coordinate(
        decompressedX.getValue(c.x),
        decompressedY.getValue(c.y)
    )
}

class Day09:Solver {
    private fun coordinateOnLine(c: Coordinate, line: Pair<Coordinate, Coordinate>): Boolean {
        val a = line.first
        val b = line.second
        return (c.distance(a) + c.distance(b)) - a.distance(b) < 0.001
    }
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val coordinates = lines.map { line ->
            val parts = line.split(",").map { it.toInt() }
            Coordinate(parts[0], parts[1])
        }

        return if(!partTwo) {
            part1(coordinates)
        } else {
            part2(coordinates)
        }
    }

    private fun part1(coordinates: List<Coordinate>): String {
        val sizes = mutableListOf<Long>()
        for (c1 in coordinates) {
            for (c2 in coordinates) {
                if (c1 == c2) {
                    continue
                }
                val p = c1.difference(c2)
                sizes.add(
                    (abs(p.first) + 1).toLong() * (abs(p.second)+1).toLong()
                )
            }
        }
        return sizes.max().toString()
    }

    private fun part2(coordinates: List<Coordinate>): String {
        val pairs = coordinates.zip(coordinates.drop(1) + coordinates.take(1))
        val compressor = CoordinateCompressor(coordinates)
        val compress = fun (c: Coordinate): Coordinate = compressor.compress(c)
        val compressedPairs = pairs.map { Pair(compress(it.first), compress(it.second)) }
        val colored = mutableSetOf<Coordinate>()
        findPolygonEdges(compressedPairs, colored)
        //Debug draw rect DrawRect.ui(colored)
        fillPolygon(compressor, colored, compressedPairs)
        val rectangles = determineRectangles(coordinates)
        for ((c1,c2, area) in rectangles) {
            val p = Pair(compress(c1), compress(c2))
            if(isValidCompressed(p, colored)) {
                return area.toString()
            }
        }

        return "Fail"
    }

    private fun determineRectangles(coordinates: List<Coordinate>): MutableList<Triple<Coordinate, Coordinate, Long>> {
        val toCheck = mutableListOf<Triple<Coordinate, Coordinate, Long>>()
        for (c1i in coordinates.indices) {
            val c1 = coordinates[c1i]
            for (c2i in (c1i + 1)..coordinates.lastIndex) {
                val c2 = coordinates[c2i]
                val p = c1.difference(c2)
                val area = (abs(p.first) + 1).toLong() * (abs(p.second) + 1).toLong()
                toCheck.add(Triple(c1, c2, area))
            }
        }
        toCheck.sortByDescending { it.third }
        return toCheck
    }

    private fun fillPolygon(
        compressor: CoordinateCompressor,
        colored: MutableSet<Coordinate>,
        compressedPairs: List<Pair<Coordinate, Coordinate>>
    ) {
        while (true) {
            val x = compressor.compressedX.values.random()
            val y = compressor.compressedY.values.random()
            val c = Coordinate(x, y)
            if (c in colored) {
                continue
            }
            val w = calculateWindingNumber(x, y, compressedPairs)
            if (w != 0) {
                fill(c, colored)
                break
            }
        }
    }

    private fun findPolygonEdges(
        compressedPairs: List<Pair<Coordinate, Coordinate>>,
        colored: MutableSet<Coordinate>
    ) {
        for (pair in compressedPairs) {
            val p1 = pair.first
            val p2 = pair.second
            if (p1.x == p2.x) {
                val minY = min(p1.y, p2.y)
                val maxY = max(p1.y, p2.y)
                for (y in minY..maxY) {
                    colored.add(Coordinate(p1.x, y))
                }
            } else {
                val minX = min(p1.x, p2.x)
                val maxX = max(p1.x, p2.x)
                for (x in minX..maxX) {
                    colored.add(Coordinate(x, p1.y))
                }
            }
        }
    }

    private fun isValidCompressed(
        p: Pair<Coordinate, Coordinate>,
        colored: Set<Coordinate>
    ): Boolean {
        val minX = min(p.first.x, p.second.x)
        val maxX = max(p.first.x, p.second.x)
        val minY = min(p.first.y, p.second.y)
        val maxY = max(p.first.y, p.second.y)
        for(y in minY..maxY) {
            val c1 = Coordinate(minX, y)
            val c2 = Coordinate(maxX, y)
            if(c1 !in colored || c2 !in colored) {
                return false
            }
        }
        for(x in minX..maxX) {
            val c1 = Coordinate(x, minY)
            val c2 = Coordinate(x, maxY)
            if(c1 !in colored || c2 !in colored) {
                return false
            }
        }
        return true
    }

    private fun fill(c: Coordinate, colored: MutableSet<Coordinate>) {
        val toFill = mutableListOf(c)
        while(toFill.isNotEmpty()) {
            val next = toFill.removeFirst()
            if(next !in colored) {
                colored.add(next)
                for (d in Direction.entries) {
                    toFill.add(next + d)
                }
            }
        }
    }

    private fun calculateWindingNumber(
        x: Int,
        y: Int,
        vertices: List<Pair<Coordinate, Coordinate>>
    ): Int {
        var winding = 0
        val hitVertices = vertices
            .filter { it.first.x == it.second.x && it.first.x >= x }
            .filter {
                val vMaxY = max(it.first.y, it.second.y)
                val vMinY = min(it.first.y, it.second.y)
                y in (vMinY..vMaxY)
            }

        for (v in hitVertices) {
            if (v.first.y < y && y < v.second.y) {
                winding -= 2
            } else if (v.first.y > y && y > v.second.y) {
                winding += 2
            } else if (v.first.y == y && y < v.second.y) {
                winding -= 1
            } else if (v.first.y == y && y > v.second.y) {
                winding += 1
            } else if (v.first.y < y && y == v.second.y) {
                winding -= 1
            } else if (v.first.y > y && y == v.second.y) {
                winding += 1
            } else {
                throw IllegalArgumentException("Invalid mix of x,y and winding number")
            }
        }
        return winding
    }
}