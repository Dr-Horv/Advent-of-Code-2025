package se.horv.day02

import se.horv.util.Solver

class Day02:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val series = lines.first().split(",")
        var invalidsSum = 0L
        for (s in series) {
            val parts = s.split("-")
            val start = parts[0].toLong()
            val end = parts[1].toLong()

            for (i in start .. end) {

                val si = i.toString()
                val isInvalid = if (!partTwo) isInvalidPart1(si) else {
                    isInvalidPart2(si)
                }
                if(isInvalid)  {
                    invalidsSum += i
                }
            }
        }

        return invalidsSum.toString()
    }

    private fun isInvalidPart2(s: String): Boolean {
        for (size in s.length/2 downTo 1) {
            val chunks = s.chunked(size)
            val first = chunks.first()
            val allSame = chunks.drop(1).all { it == first }
            if (allSame) {
                return true
            }
        }

        return false

    }

    private fun isInvalidPart1(s: String): Boolean {
        val part1 = s.slice(IntRange(0, s.lastIndex / 2))
        val part2 = s.slice(IntRange(s.lastIndex / 2 + 1, s.lastIndex))
        return part1 == part2
    }
}