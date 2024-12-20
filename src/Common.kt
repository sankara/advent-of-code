data class Coord(val x: Int, val y: Int) {
    operator fun plus(other: Coord) = Coord(x + other.x, y + other.y)
    operator fun minus(other: Coord) = Coord(x - other.x, y - other.y)
    operator fun times(scalar: Int) = Coord(x * scalar, y * scalar)

    override fun toString(): String {
        return "($x, $y)"
    }
}

fun <L> Iterable<L>.print(str: String = ""): Iterable<L> {
    println("$str$this")
    return this
}

fun <L> Iterable<L>.withoutItemAt(index: Int) = filterIndexed { i, _ -> i != index }
