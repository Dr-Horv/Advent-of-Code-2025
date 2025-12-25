package se.horv.day10

import se.horv.util.Solver

var currId = 0
fun generateId(): Int = currId++

private val memory = mutableMapOf<Pair<List<Int>, Machine>, Int>()

private data class Machine(
    val id: Int = generateId(),
    val targetLights: Int,
    val lights: Int,
    val buttons: Map<Int, Int>,
    val joltageRequirements: List<Int>
) {
    fun machineStateFromButtonPress(b: Int): Machine {
        val toSwitch = buttons.getValue(b)
        val newLights = lights xor toSwitch
        return copy(
            lights = newLights,
        )
    }

    fun lightsToString(l: Int): String = "[${l.toString(2).map { if (it == '1') "#" else "." }}]"
    override fun toString(): String = lightsToString(lights)
    fun verboseToString(): String = "${toString()} ${lightsToString(targetLights)}"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Machine

        if (id != other.id) return false
        if (targetLights != other.targetLights) return false
        if (lights != other.lights) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + targetLights
        result = 31 * result + lights
        return result
    }

}

private data class State(val m: Machine, val presses: Set<Int>)

class Day10:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val machines = mutableListOf<Machine>()
        for (line in lines) {
            val parts = line.split(" ")
            val targetLightsString = parts[0].drop(1).dropLast(1).map { if(it == '#') "1" else "0" }.joinToString("")
            val targetLights = targetLightsString.toInt(2)
            val buttons = parts.drop(1).dropLast(1)
                .map { buttonDef ->
                    val lightsHit = buttonDef.drop(1).dropLast(1).split(",").map { it.toInt() }
                    val ns = CharArray(targetLightsString.length).map { '0' }.toTypedArray()
                    lightsHit.forEach { ns[it] = '1' }
                    ns.joinToString("").toInt(2)
                }.mapIndexed { i, b -> Pair(i, b) }.toMap()

            val joltageRequirements = parts.last().drop(1).dropLast(1).split(",").map { it.toInt() }
            machines.add(
                Machine(
                    targetLights=targetLights,
                    lights = 0,
                    buttons = buttons,
                    joltageRequirements=joltageRequirements
                )
            )
        }

        val res = mutableListOf<String>()
        if(!partTwo) {
            var i = 0
            return machines.sumOf {
                val size = initialize(it, true).first().size
                i++
                println("$i/${machines.size}")
                size
            }.toString()
        } else {
            var i = 0
            return machines.sumOf {
                val r = recurse(it.joltageRequirements, it)
                i++
                println("$i/${machines.size}")
                res.add(r.toString())
                r.toLong()
            }.toString()
        }
    }

    private fun recurse(target: List<Int>, m: Machine): Int {
        val memoryKey = Pair(target, m)
        if(memory.containsKey(memoryKey)) {
            return memory.getValue(memoryKey)
        }
        if(target.all { it == 0 }) return 0
        val targetLights = target.joinToString("") { if (it % 2 == 0) "0" else "1" }.toInt(2)
        val candidates = initialize(
            m.copy(targetLights = targetLights),
            false
        )
        if(candidates.isEmpty()) {
            memory[memoryKey] = 10_000
            return 10_000
        }
        val results = mutableListOf<Int>()
        for (c in candidates) {
            val nextTarget = calculateTargetsFromCandidate(c, m, target)
            if(nextTarget.any { it < 0 }) {
                continue
            }
            results.add(
                2 * recurse(nextTarget, m) + c.size
            )
        }
        if(results.isEmpty()) {
            memory[memoryKey] = 10_000
            return 10_000
        }
        val minRes = results.min()
        memory[memoryKey] = minRes
        return minRes
    }

    private fun calculateTargetsFromCandidate(
        c: Set<Int>,
        m: Machine,
        target: List<Int>
    ): List<Int> {
        val ps = c
            .map {
                val bv = m.buttons.getValue(it)
                val bin = bv.toString(2).padStart(target.size, '0')
                val l = mutableListOf<Int>()
                 bin.forEachIndexed { i, s ->
                    if (s == '1') {
                        l.add(i)
                    }
                }
                l
            }
        val js = target.toMutableList()
        ps.forEach {
            it.forEach { i ->
                js[i] = js[i] - 1
            }
        }
        val hjs = js.map { it / 2 }
        return hjs
    }

    private fun initialize(machine: Machine, greedy: Boolean = false): List<Set<Int>> {
        val initial = State(machine, emptySet())
        val toExplore = mutableSetOf(initial)
        val toExploreList = mutableListOf(initial)
        fun hasReachedTarget(s: State): Boolean = s.m.lights == s.m.targetLights
        val result = mutableListOf<Set<Int>>()
        while (toExploreList.isNotEmpty()) {
            val next = toExploreList.removeAt(0)
            if(hasReachedTarget(next)) {
                result.add(next.presses)
                if(greedy) {
                    return result
                }
            }

            for (b in next.m.buttons.keys) {
                if(b in next.presses) {
                    continue
                }
                val ns = State(next.m.machineStateFromButtonPress(b), next.presses + b)
                if(!toExplore.contains(ns)) {
                    toExplore.add(ns)
                    toExploreList.add(ns)
                }
            }
        }
        return result
    }
}