import java.io.File
import java.util.*
import kotlin.math.abs

fun main() {
    // Create a Scanner object to read the file
    val inputFile = File("resources/day-1.input.txt")
    var scanner = Scanner(inputFile)

    // Count the number of lines in the file
    var lineCount = 0
    while (scanner.hasNextLine()) {
        scanner.nextLine()
        lineCount++
    }

    // Create an array to store the data
    val array1 = IntArray(lineCount)
    val array2 = IntArray(lineCount)

    // Read each line of the file and split it on whitespace
    scanner.close()
    scanner = Scanner(inputFile)
    for (i in 0 until lineCount) {
        //Damn - no destructured assignment
        val data = scanner.nextLine().split("   ").map({ it.toInt() })
        array1[i] = data[0]
        array2[i] = data[1]
    }


    println("Sum: ${array1.sorted().zip(array2.sorted()) { a, b -> abs(a - b) }.sum()}")
    println("Similarity: ${array1.sumOf { a -> array2.filter { b -> a == b }.sum() }}")
}
