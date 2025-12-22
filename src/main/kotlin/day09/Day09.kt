package se.horv.day09

import se.horv.util.Coordinate
import se.horv.util.Solver
import kotlin.math.abs


class Day09:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val coordinates = lines.map { line ->
            val parts = line.split(",").map { it.toInt() }
            Coordinate(parts[0], parts[1])
        }
        val sizes = mutableListOf<Long>()
        for (c1 in coordinates) {
            for (c2 in coordinates) {
                if(c1 == c2) {
                    continue
                }
                val p = c1.difference(c2)
                sizes.add(
                    (abs(p.first + 1).toLong() * abs(p.second + 1).toLong())
                )
            }
        }
       return sizes.max().toString()

        val s = mutableSetOf<Coordinate>()
        val s2 = mutableSetOf<Coordinate>()
        
    }
}