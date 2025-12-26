package day12

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import se.horv.day12.Day12

class Day12Test {

    @Test
    fun part1() {
        val result = Day12().solve(
            """0:
###
##.
##.

1:
###
##.
.##

2:
.##
###
##.

3:
##.
###
##.

4:
###
#..
###

5:
###
.#.
###

4x4: 0 0 0 0 2 0
12x5: 1 0 1 0 2 2
12x5: 1 0 1 0 3 2""".split("\n"), false
        )

        assertEquals("2", result)
    }

    @Test
    fun part2() {
        val result = Day12().solve(
            """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82""".split("\n"), true
        )

        assertEquals("6", result)
    }
}