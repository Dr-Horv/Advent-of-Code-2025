package se.horv.day03

import se.horv.util.Solver

class Day03:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        var sum = 0L
        for (line in lines) {
            val numbers = line
                .split("")
                .filter { it.isNotEmpty() }
                .map { it.toInt() }
            val joltage = findLargestJoltage(numbers, partTwo)
            sum += joltage
        }
        return sum.toString()
    }

    private fun findLargestJoltage(batteries: List<Int>, partTwo: Boolean): Long {
        val digits = if (!partTwo) { 2 } else { 12 }
        val numbers = mutableListOf<Int>()
        var remaining = batteries
        while (numbers.size != digits) {
            val toSave = digits - numbers.size - 1
            val res = findNext(remaining, toSave)
            numbers += res.first
            remaining = res.second
        }
        val derp = numbers.joinToString("")
        return numbers
            .joinToString("")
            .toLong()
    }

    private fun findNext(remaining: List<Int>, toSave: Int): Pair<Int, List<Int>> {
        var max = remaining[0]
        var index = 0
        val maxIndex = remaining.size - toSave
        for ((i: Int, n: Int) in remaining.withIndex()) {
            if(i == maxIndex) {
                break
            }
            if(n > max) {
                max = n
                index = i
            }
        }
        return Pair(max, remaining.drop(index+1))

    }
}