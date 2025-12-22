package day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import se.horv.day08.Day08

class Day08Test {

    @Test
    fun part1() {
        val result = Day08().solve(
            """162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689""".split("\n"), false
        )

        assertEquals("40", result)
    }


    @Test
    fun part2() {
        val result = Day08().solve(
            """162,817,812
57,618,57
906,360,560
592,479,940
352,342,300
466,668,158
542,29,236
431,825,988
739,650,466
52,470,668
216,146,977
819,987,18
117,168,530
805,96,715
346,949,466
970,615,88
941,993,340
862,61,35
984,92,344
425,690,689""".split("\n"), true
        )

        assertEquals("25272", result)
    }


}