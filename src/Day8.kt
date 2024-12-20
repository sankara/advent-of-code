import kotlin.io.path.Path
import kotlin.io.path.readLines

class Day8 {
    private val map = readInput()

    fun run() {
        val projectedMap = map.copy()
        val count =
            map.keys().map { projectAntiNodes(projectedMap, it) }.print("Projected Nodes: ").flatten().toSet().count()
        println("Count: $count")
        println(projectedMap)

        val resonantMap = map.copy()
        println(
            "Resonant Count: ${
                map.keys().map { projectAntiNodes(resonantMap, it, true) }.print(" Resonants : ").flatten().toSet()
                    .count()
            }"
        )
        println(resonantMap)
    }

    private fun projectAntiNodes(map: AntennaMap, c: Char, resonance: Boolean = false): List<Coord> {
        val locs = map[c]!!
        val antiNodes: MutableList<Coord> = mutableListOf()
        for (i in locs.indices) {
            for (j in i + 1 until locs.size) {
                antiNodes += resonantNodes(map, locs[i], locs[j], true, resonance)
                antiNodes += resonantNodes(map, locs[i], locs[j], false, resonance)
                if (resonance) {
                    antiNodes += locs[i]
                    antiNodes += locs[j]
                }
            }
        }

        map.markAntinodes(antiNodes)
        return antiNodes
    }

    private fun resonantNodes(
        map: AntennaMap,
        pos1: Coord,
        pos2: Coord,
        dir: Boolean,
        resonance: Boolean
    ): MutableList<Coord> {
        var count = 1
        val antiNodes: MutableList<Coord> = mutableListOf()
        do {
            val newPos = if (dir) pos2 + (pos2 - pos1) * count++ else pos1 - (pos2 - pos1) * count++
            val withinBounds = map.withinBounds(newPos)
            if (withinBounds) {
                antiNodes += newPos
            }
        } while (resonance && withinBounds)
        return antiNodes
    }

    data class AntennaMap(val n: Int, val m: Int) {
        private val map = Array(n) { Array(m) { '.' } }
        private val summaryMap = mutableMapOf<Char, MutableList<Coord>>()

        operator fun set(coord: Coord, c: Char) {
            map[coord.x][coord.y] = c
            if (summaryMap.contains(c)) summaryMap[c]?.add(coord) else summaryMap[c] = mutableListOf(coord)
        }

        operator fun get(c: Char) = summaryMap[c]
        operator fun get(coord: Coord) = map[coord.x][coord.y]
        fun keys() = summaryMap.keys

        override fun toString(): String {
            return map.joinToString("\n") { it.joinToString("") }
        }

        fun withinBounds(coord: Coord): Boolean {
            return coord.x in 0..<n && coord.y in 0..<m
        }

        fun copy(): AntennaMap {
            val newMap = AntennaMap(this.n, this.m)
            for (i in 0..<n)
                for (j in 0..<m)
                    newMap[Coord(i, j)] = this[Coord(i, j)]
            return newMap
        }

        fun markAntinodes(antiNodes: List<Coord>) = antiNodes.forEach { if (this[it] == '.') this[it] = '#' }
    }


    private fun readInput(): AntennaMap {
        val lines = Path("resources/day-8.input.txt").readLines()
        val antennaMap = AntennaMap(lines.size, lines[0].length)
        lines.forEachIndexed { i, line ->
            line.forEachIndexed { j, c ->
                if (c.isLetterOrDigit()) {
                    antennaMap[Coord(i, j)] = c
                }
            }
        }
        return antennaMap
    }

}

fun main() {
    Day8().run()
}
