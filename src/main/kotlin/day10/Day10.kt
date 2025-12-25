package se.horv.day10

import se.horv.util.Solver

var currId = 0
fun generateId(): Int = currId++

private data class Machine(
    val targetLights: Int,
    val lights: Int,
    val buttons: Map<Int, Int>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Machine

        return lights == other.lights
    }
    override fun hashCode(): Int {
        return lights.hashCode()
    }

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
                )
            )
        }

        var i = 0
        return machines.sumOf {
            val size = initialize(it).size
            i++
            println("$i/${machines.size}")
            size
        }.toString()
    }

    private fun initialize(machine: Machine): Set<Int> {
        val initial = State(machine, emptySet())
        val toExplore = mutableSetOf(initial)
        val toExploreList = mutableListOf(initial)
        fun hasReachedTarget(s: State): Boolean = s.m.lights == s.m.targetLights
        while (toExploreList.isNotEmpty()) {
            val next = toExploreList.removeAt(0)
            if(hasReachedTarget(next)) {
                return next.presses
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
        throw IllegalStateException("Can't initialize machine")
    }
}