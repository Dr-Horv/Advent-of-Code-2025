package day03

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import se.horv.day03.Day03

class Day03Test {

    @Test
    fun part1() {
        val result = Day03().solve(
            """987654321111111
811111111111119
234234234234278
818181911112111""".split("\n"), false
        )

        assertEquals("357", result)
    }


    @Test
    fun part2() {
        val result = Day03().solve(
            """987654321111111
811111111111119
234234234234278
818181911112111""".split("\n"), true
        )

        assertEquals("3121910778619", result)
    }


}