import java.math.BigDecimal
import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val inputStr = Path("resources/day-3.input.txt").readText()
    val matches = Regex("(do\\(\\)|don't\\(\\)|(mul\\((\\d{1,3}),(\\d{1,3})\\)))").findAll(inputStr)
    val sum = matches.fold(Pair(BigDecimal(0), true)) { (sum, enabled), match ->
        when (match.groupValues[0]) {
            "don't()" -> Pair(sum, false)
            "do()" -> Pair(sum, true)
            else -> {
                val (a, b) = match.groupValues.slice(3..4).map { it.toBigDecimal() }
                if (enabled) Pair(sum + a * b, enabled) else (Pair(sum, enabled))
            }
        }
    }
    println("Sum: ${sum.first}")
    //Sum: 188116424
    //Sum: 104245808
}