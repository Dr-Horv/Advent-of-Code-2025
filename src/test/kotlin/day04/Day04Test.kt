package day04

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import se.horv.day04.Day04

class Day04Test {

    @Test
    fun part1() {
        val result = Day04().solve(
            """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.""".split("\n"), false
        )

        assertEquals("13", result)
    }


    @Test
    fun part2() {
        val result = Day04().solve(
            """..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.""".split("\n"), true
        )

        assertEquals("43", result)
    }


}