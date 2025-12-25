package day11

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import se.horv.day11.Day11

class Day11Test {

    @Test
    fun part1() {
        val result = Day11().solve(
            """aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out""".split("\n"), false
        )

        assertEquals("5", result)
    }

    @Test
    fun part2() {
        val result = Day11().solve(
            """svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out""".split("\n"), true
        )

        assertEquals("2", result)
    }
}