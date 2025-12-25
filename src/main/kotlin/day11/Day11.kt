package se.horv.day11

import se.horv.util.Solver

data class Device(val id: String, val outputs: List<String>)

private val memory = mutableMapOf<Pair<String, String>, Int>()

class Day11:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val devices = lines.map {
            val parts = it.split(":")
            val id = parts[0]
            val outputs = parts[1].trim().split(" ").map { o -> o.trim() }
            Device(id, outputs)
        }
        val allDevices = devices + Device("out", emptyList())

        if(!partTwo) {
            val youDevice = devices.first { it.id == "you" }
            val paths = findPaths(youDevice, allDevices, currPath = listOf(youDevice.id))
            return paths.size.toString()
        } else {
            val map = allDevices.associate { Pair(it.id, it.outputs) }
            val p1 = findPaths2("svr", "fft", map)
            val p2 = findPaths2("fft", "dac", map)
            val p3 = findPaths2("dac", "out", map)
            val part1 = p1.toLong() * p2 * p3
            val p4 = findPaths2("svr", "dac", map)
            val p5 = findPaths2("dac", "fft", map)
            val p6 = findPaths2("fft", "out", map)
            val part2 = p4.toLong() * p5 * p6
            return (part1 + part2).toString()
        }

    }

    private fun findPaths(d: Device, devices: List<Device>, currPath: List<String>, target: String="out"): List<List<String>> {
        if(d.id == target) {
            return listOf(currPath)
        }

        return d.outputs.filter { it !in currPath }.flatMap { o ->
            val n = devices.first { it.id == o }
            findPaths(n, devices, currPath + o, target)
        }
    }

    private fun findPaths2(start: String, target: String, map: Map<String, List<String>>): Int {
        val key = Pair(start, target)
        if(memory.containsKey(key)) {
            return memory.getValue(key)
        }
        if(start == target) {
            memory[key] = 1
            return 1
        }
        val res = map.getValue(start).sumOf {
            findPaths2(it, target, map)
        }
        memory[key] = res
        return res
    }
}