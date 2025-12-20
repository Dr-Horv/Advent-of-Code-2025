package se.horv.day05

import se.horv.util.Solver
import kotlin.math.max
import kotlin.math.min


private data class IngredientRange(val from: Long, val to: Long) {
    operator fun contains(i: Long): Boolean = i in from..to
    fun canMerge(other: IngredientRange): Boolean = other.from in this || other.to in this || this.from in other || this.to in other
    fun merge(other: IngredientRange): IngredientRange {
        val min = min(from, other.from)
        val max = max(to, other.to)
        return IngredientRange(min, max)
    }
}

class Day05:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val ranges = lines
            .takeWhile { it.contains("-") }
            .map {
                val nbrs = it.split("-").map(String::toLong)
                IngredientRange(nbrs[0], nbrs[1])
            }

        if(!partTwo) {
            val ingredients = lines
                .dropWhile { it.isNotEmpty() }
                .drop(1)
                .map { it.toLong() }

            return ingredients.count { ingredient ->
                ranges.any { ingredient in it }
            }.toString()
        } else {
            val sortedRanges = ranges.sortedBy { it.from }
            var current = sortedRanges.first()
            val trueRanges = mutableSetOf<IngredientRange>()
            for (range in sortedRanges.drop(1)) {
                if(range.canMerge(current)) {
                    current = current.merge(range)
                } else {
                    trueRanges.add(current)
                    current = range
                }
            }
            trueRanges.add(current)

            return trueRanges.sumOf { it.to - it.from + 1 }.toString()
        }

    }

}