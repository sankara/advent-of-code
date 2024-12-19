import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.pow

fun main() {
    val opers: List<Pair<String, (Long, Long) -> Long>> = listOf(
        "+" to { a, b -> a + b },
        "*" to { a, b -> a * b },
        "||" to { a, b -> (a.toString() + b.toString()).toLong() })
    val sum = readInputs().filter { (res, nums) -> checkCombinations(res, nums, opers) }.sumOf { it.first }
    println("Sum = $sum")
}

private fun readInputs(): List<Pair<Long, List<Long>>> {
    return Path("resources/day-7.input.txt").readLines().map { line ->
        val (head, tail) = line.split(":")
        val res = head.trim().toLong()
        val nums = tail.trim().split(" ").map(String::trim).map(String::toLong)
        res to nums
    }
}

private fun checkCombinations(res: Long, values: List<Long>, opers: List<Pair<String, (Long, Long) -> Long>>): Boolean {
    println("res: $res, values: $values")
    val numOps = opers.size.toDouble()
    for (i in 0..<numOps.pow(values.size - 1).toInt()) {
        var sum = values[0]
        for (j in values.size - 2 downTo 0) {
            val oper = (i / numOps.pow(j)).mod(numOps).toInt()
            sum = opers[oper].second(sum, values[values.size - j - 1])
        }
        if (sum == res) return true
    }
    return false
}