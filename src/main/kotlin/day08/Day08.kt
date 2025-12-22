package se.horv.day08

import se.horv.util.Position
import se.horv.util.Solver

private data class Circuit(val junctions: Set<Position>) {
    operator fun contains(position: Position) = junctions.contains(position)
    operator fun plus(other: Circuit): Circuit = Circuit(junctions + other.junctions)
}

class Day08:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val junctions = lines.map {
            val parts = it.split(",").map { pos -> pos.trim().toInt() }
            Position(parts[0], parts[1], parts[2])
        }
        val circuits = junctions.map { Circuit(setOf(it)) }.toMutableSet()

        val distances = mutableMapOf<Pair<Position, Position>, Double>()
        for (j1i in junctions.indices) {
            val j1 = junctions[j1i]
            for (j2i in (j1i+1) .. junctions.lastIndex) {
                val j2 = junctions[j2i]
                distances[Pair(j1, j2)] = j1.euclideanDistance(j2)
            }
        }
        val sortedDistances = distances.entries.sortedBy { it.value }.map { it.key }
        if(!partTwo) {
            sortedDistances.take(1000)
                .forEach { e ->
                    val c1 = circuits.first { e.first in it }
                    val c2 = circuits.first { e.second in it }
                    if(c1 != c2) {
                        circuits.remove(c1)
                        circuits.remove(c2)
                        circuits.add(c1 + c2)
                    }
                }

            return circuits.sortedByDescending { it.junctions.size }
                .take(3)
                .fold(1) {acc, c -> acc * c.junctions.size }
                .toString()
        } else {
            var i = 0
            for (e in sortedDistances) {
                val c1 = circuits.first { e.first in it }
                val c2 = circuits.first { e.second in it }
                if(c1 != c2) {
                    circuits.remove(c1)
                    circuits.remove(c2)
                    circuits.add(c1 + c2)
                }
                if(circuits.size == 1) {
                    return (e.first.x.toLong() * e.second.x.toLong()).toString()
                }
                i++
            }
            return ""
        }
    }
}