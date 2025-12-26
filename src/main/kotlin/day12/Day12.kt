package se.horv.day12

import se.horv.util.Coordinate
import se.horv.util.Solver

private data class PresentShape(val index: Int, val shape: Set<Coordinate>)
private data class Region(val width: Int, val length: Int, val shapeQuantities: Map<Int, Int>) {
    fun area(): Int = width * length
}

class Day12:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val it = lines.iterator()
        val presentShapes = mutableMapOf<Int, PresentShape>()
        val regions = mutableListOf<Region>()
        while (it.hasNext()) {
            val line = it.next().trim()
            if(line.isEmpty()) {
                continue
            }
            if(!line.contains("x")) {
                val index = line.split(":").first().trim().toInt()
                val shape = mutableSetOf<Coordinate>()
                for (y in 0 until 3) {
                    val l = it.next()
                    for (x in 0 until 3) {
                        val c = l[x]
                        if(c == '#') {
                            shape.add(Coordinate(x, y))
                        }
                    }
                }
                presentShapes[index] = PresentShape(index, shape)
            } else if (line.contains("x")) {
                val parts = line.split(":")
                val dimensions = parts[0].split("x").map { it.toInt() }
                val quantities = parts[1].trim().split(" ")
                    .mapIndexed { i, q -> Pair(i, q.toInt()) }
                    .toMap()
                regions.add(Region(dimensions[0], dimensions[1], quantities))
            }
        }

        return part1(regions, presentShapes)
    }

    private fun part1(
        regions: List<Region>,
        presentShapes: Map<Int, PresentShape>
    ): String {
        var maybeFit = 0
        var fits = 0
        var doesntFit = 0
        for (region in regions) {
            val area = region.area()
            val overEstimate = region.shapeQuantities.values.sumOf { it * 9 }
            if(overEstimate <= area) {
                fits++
                continue
            }

            val perfectPacking = region.shapeQuantities.entries.sumOf { e ->
                val index = e.key
                val quanity = e.value
                val presentShape = presentShapes.getValue(index)
                val neededSpaces = presentShape.shape.size
                val totalSize = quanity * neededSpaces
                totalSize
            }

            if(perfectPacking > area) {
                doesntFit++
                continue
            }

            maybeFit++
        }

        if(maybeFit == 0) {
            return fits.toString()
        } else {
            throw Error("Unclear cases left")
        }

    }
}