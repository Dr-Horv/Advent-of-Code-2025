package se.horv.day07

import se.horv.util.Coordinate
import se.horv.util.Direction
import se.horv.util.Solver

enum class Tile {
    EMPTY,
    SOURCE,
    BEAM,
    SPLITTER;

    override fun toString(): String = when(this) {
        SOURCE -> "S"
        BEAM -> "|"
        SPLITTER -> "^"
        EMPTY -> "."
    }
}

val memory = mutableMapOf<Coordinate, Long>()

class Day07:Solver {
    override fun solve(lines: List<String>, partTwo: Boolean): String {
        val map = mutableMapOf<Coordinate, Tile>()

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                when(c) {
                    '.' -> map[Coordinate(x, y)] = Tile.EMPTY
                    '^' -> map[Coordinate(x, y)] = Tile.SPLITTER
                    'S' -> map[Coordinate(x, y)] = Tile.SOURCE
                }
            }
        }

        val maxY = map.keys.maxByOrNull { it.y }!!.y
        val maxX = map.keys.maxByOrNull { it.x }!!.x

        if(!partTwo) {
            return part1(map, maxY, maxX)
        } else {
            return part2(map, maxY, maxX)
        }


    }

    private fun part2(
        map: MutableMap<Coordinate, Tile>,
        maxY: Int,
        maxX: Int
    ): String {
        part1(map, maxY, maxX)
        val mapDefaultEmpty = map.withDefault { Tile.EMPTY }
        val beamSource = map.entries.first { it.value == Tile.SOURCE }
        val splitterSources = calculateSplitterSources(beamSource, mapDefaultEmpty)
        val endBeams = map.entries.filter { it.key.y == maxY && it.value == Tile.BEAM }
        val beamSources = calculateBeamSources(endBeams, mapDefaultEmpty)

        val res = endBeams.sumOf {
            val sources = beamSources.getValue(it.key)
            var sum = 0L
            for (source in sources) {
                val r = reverseCalculate(source, splitterSources, beamSource.key)
                sum += r
            }
            sum
        }

        return res.toString()

    }

    private fun calculateBeamSources(
        endBeams: List<MutableMap.MutableEntry<Coordinate, Tile>>,
        mapDefaultEmpty: MutableMap<Coordinate, Tile>
    ): MutableMap<Coordinate, List<Coordinate>> {
        val beamSources = mutableMapOf<Coordinate, List<Coordinate>>()
        for (b in endBeams) {
            val sources = mutableListOf<Coordinate>()
            var c = b.key
            while (true) {
                c = c.plus(Direction.UP)
                if (mapDefaultEmpty.getValue(c) == Tile.EMPTY) {
                    break
                }
                val left = c.plus(Direction.LEFT)
                val right = c.plus(Direction.RIGHT)
                if (mapDefaultEmpty.getValue(left) == Tile.SPLITTER) {
                    sources.add(left)
                }
                if (mapDefaultEmpty.getValue(right) == Tile.SPLITTER) {
                    sources.add(right)
                }
                if (c.y == 0 && mapDefaultEmpty.getValue(c) == Tile.SOURCE) {
                    sources.add(c)
                }
            }
            beamSources[b.key] = sources
        }
        return beamSources
    }

    private fun calculateSplitterSources(
        beamSource: MutableMap.MutableEntry<Coordinate, Tile>,
        mapDefaultEmpty: MutableMap<Coordinate, Tile>
    ): MutableMap<Coordinate, List<Coordinate>> {
        val splitters = mapDefaultEmpty.entries.filter { it.value == Tile.SPLITTER }
        val splitterSources = mutableMapOf<Coordinate, List<Coordinate>>()
        splitterSources[beamSource.key] = emptyList<Coordinate>()

        for (s in splitters) {
            val sources = mutableListOf<Coordinate>()
            var c = s.key
            while (c.y > 0) {
                c = c.plus(Direction.UP)
                if (mapDefaultEmpty.getValue(c) == Tile.SPLITTER) {
                    break
                }

                val left = c.plus(Direction.LEFT)
                val right = c.plus(Direction.RIGHT)
                if (mapDefaultEmpty.getValue(left) == Tile.SPLITTER) {
                    sources.add(left)
                }
                if (mapDefaultEmpty.getValue(right) == Tile.SPLITTER) {
                    sources.add(right)
                }
                if (c.y == 0 && mapDefaultEmpty.getValue(c) == Tile.SOURCE) {
                    sources.add(c)
                }
            }
            splitterSources[s.key] = sources
        }
        return splitterSources
    }

    private fun reverseCalculate(
        splitter: Coordinate,
        splitterSources: Map<Coordinate, List<Coordinate>>,
        beamSource: Coordinate
    ): Long {
        if(memory.contains(splitter)) {
            return memory.getValue(splitter)
        }
        val sources = splitterSources.getValue(splitter)
        if(splitter == beamSource) {
            return 1L
        }
        var sum = 0L
        for (source in sources) {
            sum += reverseCalculate(source, splitterSources, beamSource)
        }
        memory[splitter] = sum
        return sum
    }

    private fun part1(
        map: MutableMap<Coordinate, Tile>,
        maxY: Int,
        maxX: Int
    ): String {
        val activeBeams = mutableSetOf<Coordinate>()
        activeBeams.add(map.entries.first { it.value == Tile.SOURCE }.key)
        var sum = 0
        while (true) {
            for (beam in activeBeams.toList()) {
                val next = beam.plus(Direction.DOWN)
                if (next.y > maxY) {
                    activeBeams.remove(beam)
                    continue
                }
                val nextTile = map.getValue(next)
                if (nextTile == Tile.EMPTY) {
                    map[next] = Tile.BEAM
                    activeBeams.add(next)
                } else if (nextTile == Tile.SPLITTER) {
                    val leftSplit = next.plus(Direction.LEFT)
                    val rightSplit = next.plus(Direction.RIGHT)
                    if (leftSplit.x >= 0 && map[leftSplit] == Tile.EMPTY) {
                        map[leftSplit] = Tile.BEAM
                        activeBeams.add(leftSplit)
                    }
                    if (rightSplit.x <= maxX && map[rightSplit] == Tile.EMPTY) {
                        map[rightSplit] = Tile.BEAM
                        activeBeams.add(rightSplit)
                    }
                    sum++
                }
                activeBeams.remove(beam)
            }

            if (activeBeams.isEmpty()) {
                break
            }
        }

        return sum.toString()
    }

}