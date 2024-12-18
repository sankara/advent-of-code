import kotlin.io.path.Path
import kotlin.io.path.forEachLine

fun main() {
    val rules = mutableListOf<List<Int>>()
    val updates = mutableListOf<List<Int>>()
    var processingSection = Pair(rules, '|')
    Path("resources/day-5.input.txt").forEachLine { line ->
        if (line == "") processingSection = Pair(updates, ',')
        else processingSection.first.add(line.split(processingSection.second).map { it.toInt() })
    }
    val (validUpdates, invalidUpdates) = updates.partition { isValidUpdate(it, rules) }
    //println("Valid updates: $validUpdates")
    println(validUpdates.sumOf { it.getMiddle() })
    //5208

    val comparator: Comparator<Int> = rulesComparator(rules)
    val correctedUpdates = invalidUpdates.map { it.sortedWith(comparator) }
    //println("Corrected Invalid updates: $correctedUpdates")
    println(correctedUpdates.sumOf { it.getMiddle() })
    //6732
}

fun isValidUpdate(update: List<Int>, rules: List<List<Int>>): Boolean {
    return rules.all { rule ->
        val (a, b) = rule.map { update.indexOf(it) }
        a < 0 || b < 0 || a < b
    }
}

fun <T> List<T>.getMiddle(): T = get(size / 2)

fun rulesComparator(rules: List<List<Int>>): Comparator<Int> {
    return java.util.Comparator<Int> { n1: Int, n2: Int ->
        for ((a, b) in rules) {
            if (a == n1 && b == n2) return@Comparator -1
            else if (a == n2 && b == n1) return@Comparator 1
            else continue
        }
        return@Comparator 0
    }
}
