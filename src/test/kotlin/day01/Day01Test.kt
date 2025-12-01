package day01

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import se.horv.day01.Day01

class Day01Test {

    @Test
    fun part1() {
        val result = Day01().solve(
            """L68
L30
R48
L5
R60
L55
L1
L99
R14
L82""".split("\n"), false
        )

        assertEquals("3", result)
    }

    @Test
    fun part2() {
        val result = Day01().solve(
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