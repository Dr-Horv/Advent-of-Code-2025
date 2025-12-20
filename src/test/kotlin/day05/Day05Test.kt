package day05

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import se.horv.day05.Day05

class Day05Test {

    @Test
    fun part1() {
        val result = Day05().solve(
            """3-5
10-14
16-20
12-18

1
5
8
11
17
32""".split("\n"), false
        )

        assertEquals("3", result)
    }


    @Test
    fun part2() {
        val result = Day05().solve(
            """3-5
10-14
16-20
12-18

1
5
8
11
17
32""".split("\n"), true
        )

        assertEquals("14", result)
    }


}