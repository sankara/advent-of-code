import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun main() {
    val (array1, array2) = Path("resources/day-1.input.txt")
        .readLines()
        .map { it.split("   ").map(String::toInt).zipWithNext().single() }
        .unzip()

    println("Sum: ${array1.sorted().zip(array2.sorted()) { a, b -> abs(a - b) }.sum()}")
    println("Similarity: ${array1.sumOf { a -> array2.filter { b -> a == b }.sum() }}")
}
