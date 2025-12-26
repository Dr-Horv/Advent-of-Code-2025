package se.horv

import se.horv.day01.Day01
import se.horv.day02.Day02
import se.horv.day03.Day03
import se.horv.day04.Day04
import se.horv.day05.Day05
import se.horv.day06.Day06
import se.horv.day07.Day07
import se.horv.day08.Day08
import se.horv.day09.Day09
import se.horv.day10.Day10
import se.horv.day11.Day11
import se.horv.day12.Day12

import se.horv.util.Solver
import java.io.File
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

enum class Day {
    Day01,
    Day02,
    Day03,
    Day04,
    Day05,
    Day06,
    Day07,
    Day08,
    Day09,
    Day10,
    Day11,
    Day12
}

@OptIn(ExperimentalTime::class)
fun main(vararg args: String) {
    val day = Day.Day12
    val daySolver: Solver = when(day) {
        Day.Day01 -> Day01()
        Day.Day02 -> Day02()
        Day.Day03 -> Day03()
        Day.Day04 -> Day04()
        Day.Day05 -> Day05()
        Day.Day06 -> Day06()
        Day.Day07 -> Day07()
        Day.Day08 -> Day08()
        Day.Day09 -> Day09()
        Day.Day10 -> Day10()
        Day.Day11 -> Day11()
        Day.Day12 -> Day12()
    }
    val filePath = "src/main/kotlin/${day.toString().lowercase()}/input.txt"
    val lines = File(filePath).readLines()
    val duration = measureTime {
        val result = daySolver.solve(lines, true)
        println(result)
    }
    println()
    println("${duration.inWholeMilliseconds}ms")
    println(duration.toString(DurationUnit.SECONDS, 3))
    println(duration.toString(DurationUnit.MINUTES, 2))

}