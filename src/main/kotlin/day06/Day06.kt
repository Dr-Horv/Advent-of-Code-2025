package se.horv.day06

import se.horv.util.Solver


class Day06:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        return if(!partTwo) {
            part1(lines)
        } else {
            part2(lines)
        }
    }

    private fun part2(lines: List<String>): String {
        val nbrs = lines.dropLast(1)
        val operators = lines.last()
        var operator = ' '
        var lastEmpty = -1
        var sum = 0L
        for (i in 0..operators.length) {
            if (i <= operators.lastIndex && operators[i] != ' ') {
                operator = operators[i]
            }
            if (i > operators.lastIndex || isAllEmpty(nbrs, i)) {
                val identity: Long = if (operator == '*') 1 else 0
                val op: (Long, Long) -> Long = when (operator) {
                    '*' -> { a, b -> a * b }
                    '+' -> { a, b -> a + b }
                    '-' -> { a, b -> a - b }
                    else -> throw Error("Unknown operator $operator")
                }
                var result = identity
                for (ni in (i - 1) downTo (lastEmpty + 1)) {
                    val numberS = nbrs.map { it[ni] }
                        .joinToString("")
                        .trim()


                    val number = numberS.toLong()

                    result = op(result, number)
                }
                lastEmpty = i
                sum += result
            }
        }

        return sum.toString()
    }

    private fun isAllEmpty(nbrs: List<String>, i: Int): Boolean = nbrs.all { it[i] == ' ' }

    private fun part1(lines: List<String>): String {
        val splitRegex = "\\s+".toRegex()
        val matrix = lines.dropLast(1).map {
            it.trim().split(splitRegex).map { d -> d.toLong() }
        }

        val operators = lines.last().trim().split(splitRegex)
        var sum = 0L
        for ((i, o) in operators.withIndex()) {
            val identity: Long = if (o == "*") 1 else 0
            val result = matrix.fold(identity) { acc, rest ->
                val n = rest[i]
                val next = when (o) {
                    "*" -> acc * n
                    "-" -> acc - n
                    "+" -> acc + n
                    else -> throw Error("Invalid operator $o")
                }
                next
            }
            sum += result
        }

        return sum.toString()
    }

}