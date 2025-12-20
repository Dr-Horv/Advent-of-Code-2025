package day06

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import se.horv.day06.Day06

class Day06Test {

    @Test
    fun part1() {
        val result = Day06().solve(
            """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """.split("\n"), false
        )

        assertEquals("4277556", result)
    }


    @Test
    fun part2() {
        val result = Day06().solve(
            """123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   +  """.split("\n"), true
        )

        assertEquals("3263827", result)
    }


}