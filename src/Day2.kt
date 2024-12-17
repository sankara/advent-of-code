import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs
import kotlin.math.sign

fun <L> Iterable<L>.withoutItemAt(index: Int) = filterIndexed { i, _ -> i != index }

fun main() {
    val data = Path("resources/day-2.input.txt").readLines().map { it.split(" ").map(String::toInt) }

    val (safe, unsafe) = data.partition { isSafe(it) }
    println("Results with no levels removed: ${safe.size}")

    val madeSafe = unsafe.filter { arr ->
        for (i in arr.indices) {
            if (isSafe(arr.withoutItemAt(i))) {
                return@filter true
            }
        }
        return@filter false
    }

    println("Results with 1 level removed: ${madeSafe.size}")
//    Results with no levels removed: 549
//    Results with 1 level removed: 40
}


fun isSafe(arr: List<Int>): Boolean {
    for (i in 1..<arr.lastIndex) {
        if ((arr[i - 1] - arr[i]).sign != (arr[i] - arr[i + 1]).sign ||
            abs(arr[i - 1] - arr[i]) !in 1..3 ||
            abs(arr[i] - arr[i + 1]) !in 1..3
        ) return false
    }
    return true
}