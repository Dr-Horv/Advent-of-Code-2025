package day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import se.horv.day09.Day09

class Day09Test {

    @Test
    fun part1() {
        val result = Day09().solve(
            """7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3""".split("\n"), false
        )

        assertEquals("50", result)
    }


    @Test
    fun part2() {
        val result = Day09().solve(
            """7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3""".split("\n"), true
        )

        assertEquals("24", result)
    }


}